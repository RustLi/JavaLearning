package org.lwl.designpatterns.single_instance;

/**
 * @program: javaProjects
 * @description: 单例模式
 * @author: RustLi
 * @create: 2018-11-12 13:46
 **/
public class SingleInstance {

    public static void main(String[] args) {
        SingleInstance1 singleInstance1 = SingleInstance1.getInstance();
        SingleInstance2 singleInstance2 = SingleInstance2.getInstance();
    }

}
