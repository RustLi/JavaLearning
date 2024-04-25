package org.lwl.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortTest {

    public static void main(String[] args) {
        List<SortUser> users = new ArrayList<SortUser>();
        users.add(new SortUser(3, null, 5));
        users.add(new SortUser(1, 2, 4));
        users.add(new SortUser(2, 3, 1));
        users.add(new SortUser(4, null, 3));
        users.add(new SortUser(5, 1, 2));

        // 创建比较器
        Comparator<SortUser> comparator = Comparator
                .comparing(SortUser::getTaskTime, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(SortUser::getWaitTime, Comparator.nullsLast(Comparator.naturalOrder()));

        // 对用户列表进行排序
        Collections.sort(users, comparator);

        // 打印排序结果
        for (SortUser user : users) {
            System.out.println(user);
        }
    }

    private static class SortUser{
        private Integer id;
        private Integer waitTime;

        private Integer taskTime;

        public SortUser(Integer id, Integer waitTime, Integer taskTime) {
            this.id = id;
            this.waitTime = waitTime;
            this.taskTime = taskTime;
        }
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getWaitTime() {
            return waitTime;
        }

        public void setWaitTime(Integer waitTime) {
            this.waitTime = waitTime;
        }

        public Integer getTaskTime() {
            return taskTime;
        }

        public void setTaskTime(Integer taskTime) {
            this.taskTime = taskTime;
        }
    }
}
