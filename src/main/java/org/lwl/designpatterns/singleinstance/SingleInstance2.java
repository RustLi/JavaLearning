package org.lwl.designpatterns.single_instance;

/**
 * @program: javaProjects
 * @description: 单例实现2
 * @author: RustLi
 * @create: 2018-11-12 14:16
 **/
public class SingleInstance2 {

    /**
     * 类加载的时候会进行初始化，但是不会实例化，jvm加载保证了其唯一性，满足多线程调用和效率
     * @param
     * @return
     */
    private static class Single{
        private static final SingleInstance2 INSTANCE = new SingleInstance2();
    }

    public static SingleInstance2 getInstance(){
        return Single.INSTANCE;
    }
}
