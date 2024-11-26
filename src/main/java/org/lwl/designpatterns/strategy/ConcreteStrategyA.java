package org.lwl.designpatterns.strategy;

/**
 * @program: javaProjects
 * @description: 具体策略类
 * @author: RustLi
 * @create: 2018-11-14 15:49
 **/
public class ConcreteStrategyA implements Strategy, DealStrategy{
    @Override
    public void operation() {
        System.out.println("A");
    }

    @Override
    public void dealMethod(String option) {

    }
}
