package org.lwl.utils;

//import com.zaxxer.hikari.HikariDataSource;
//import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @description: 线程池监控工具类
 * @author: liuguohong
 * @create: 2020/02/05 22:12
 */
@Component
@Slf4j(topic = "threadPoolMonitorLog")
public class ThreadPoolMonitorUtils {
//    @Autowired(required = false)
//    private HikariDataSource hikariDataSource;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1,
            new BasicThreadFactory.Builder().namingPattern("ThreadPoolMonitor").build());
    private static final CopyOnWriteArrayList<ThreadPoolExecutor> THREAD_POOL_EXECUTORS = new CopyOnWriteArrayList<>();
    private static final Map<ThreadPoolExecutor, String> NAMES = new ConcurrentHashMap<>();

    @PostConstruct
    public void print() {
        addToMonitor("ThreadPoolMonitor", executorService);

        Runnable printTask = () -> {
            StringBuilder buffer = new StringBuilder(81);
            buffer.append("线程池监控:\n| pool status | pool size | active | queued | completed | ThreadFactory\n");
            for (ThreadPoolExecutor threadPoolExecutor : THREAD_POOL_EXECUTORS) {
                printThreadPoolStatus(threadPoolExecutor, buffer);
            }
//            dataSourcePoolMonitor(buffer);
            log.info("{}", buffer);
        };
        executorService.scheduleWithFixedDelay(printTask, 60, 60, TimeUnit.SECONDS);
    }

//    private void dataSourcePoolMonitor(StringBuilder buffer) {
//        if (hikariDataSource == null) {
//            return;
//        }
//        HikariPoolMXBean hikariPoolMXBean = hikariDataSource.getHikariPoolMXBean();
//        if (hikariPoolMXBean == null) {
//            return;
//        }
//        int activeConnections = hikariPoolMXBean.getActiveConnections();
//        int idleConnections = hikariPoolMXBean.getIdleConnections();
//        int totalConnections = hikariPoolMXBean.getTotalConnections();
//        int threadsAwaitingConnection = hikariPoolMXBean.getThreadsAwaitingConnection();
//        buffer.append("Hikari Pool active:")
//                .append(activeConnections)
//                .append(", idle:")
//                .append(idleConnections)
//                .append(", total:")
//                .append(totalConnections)
//                .append(", awaiting:")
//                .append(threadsAwaitingConnection);
//    }

    /**
     * 线上有连接池获取不到的情况，不能确定连接池的情况，这里加入监控，后面可以去除
     *
     * @param tpe
     */
    private void printThreadPoolStatus(ThreadPoolExecutor tpe, StringBuilder buffer) {
        ThreadFactory threadFactory = tpe.getThreadFactory();
        String namePrefix = NAMES.get(tpe);
        if (StringUtils.isBlank(namePrefix)) {
            namePrefix = "Unknown-" + threadFactory.getClass().getSimpleName();
        }

        String terminated = tpe.isTerminated() ? "Terminated" : "Terminating";
        String status = !tpe.isShutdown() ? "Running" : terminated;
        buffer.append(String.format("| %-11s | %9d | %6d | %6d | %9d | %s%n", status, tpe.getPoolSize(), tpe.getActiveCount(), tpe.getQueue().size(), tpe.getCompletedTaskCount(), namePrefix));
    }

    /**
     * 添加到线程池的监控中
     *
     * @param executorService
     */
    public static void addToMonitor(String name, ExecutorService executorService) {
        try {
            if (executorService instanceof ThreadPoolExecutor) {
                THREAD_POOL_EXECUTORS.add((ThreadPoolExecutor) executorService);
                NAMES.put((ThreadPoolExecutor) executorService, name);
            }
        } catch (Exception e) {
            log.error("addToMonitor error", e);  //监控不影响
        }
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
        THREAD_POOL_EXECUTORS.forEach(ThreadPoolExecutor::shutdown);
        THREAD_POOL_EXECUTORS.clear();
        log.info("monitor destroy success");
    }

}
