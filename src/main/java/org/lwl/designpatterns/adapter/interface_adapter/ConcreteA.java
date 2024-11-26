package org.lwl.designpatterns.adapter.interface_adapter;

/**
 * @program: javaProjects
 * @description: 具体实现类
 * @author: RustLi
 * @create: 2018-11-13 17:35
 **/
public class ConcreteA extends Adapter{
    @Override
    public void a() {
        System.out.println("aaaaaa");
    }

    @Override
    public void c() {
        System.out.println("ccccc");
    }
}
