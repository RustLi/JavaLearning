package org.lwl.designpatterns.builder;

/**
 * @program: javaProjects
 * @description: 抽象建造者
 * @author: RustLi
 * @create: 2018-11-12 15:59
 **/
public abstract class AbstractBuilder {
    protected Car car;

    public Car getCar() {
        return car;
    }

    public void createCar() {
        car = new Car();
    }

    public abstract void buildColor();
    public abstract void buildSize();
}
