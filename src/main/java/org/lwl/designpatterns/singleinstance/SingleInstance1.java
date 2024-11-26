package org.lwl.designpatterns.single_instance;

/**
 * @program: javaProjects
 * @description: 单例实现1
 * @author: RustLi
 * @create: 2018-11-12 14:05
 **/
public class SingleInstance1 {
    
    private static SingleInstance1 sInstance;

    /**
     *  线程安全，效率高，延迟加载，双重检查
     * @param
     * @return
     */
    public static SingleInstance1 getInstance(){
        if (sInstance == null) {
            synchronized (SingleInstance1.class){
                if (sInstance == null) {
                    sInstance = new SingleInstance1();
                }
            }
        }
        return sInstance;
    }
}
