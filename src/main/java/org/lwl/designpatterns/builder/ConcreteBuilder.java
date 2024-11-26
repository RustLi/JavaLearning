package org.lwl.designpatterns.builder;

/**
 * @program: javaProjects
 * @description: 具体建造者
 * @author: RustLi
 * @create: 2018-11-12 15:59
 **/
public class ConcreteBuilder extends AbstractBuilder{

    @Override
    public void buildColor() {
        car.setColor("green");
    }

    @Override
    public void buildSize() {
        car.setSize(10);
    }
}
