package org.lwl.designpatterns.factory.AbstractFactory;

/**
 * @program: javaProjects
 * @description: intel 电脑工厂
 * @author: RustLi
 * @create: 2018-11-13 11:16
 **/
public class IntelFactory implements AbstractFactory{
    @Override
    public Cpu createCpu() {
        return new IntelCpu(157);
    }

    @Override
    public Board createBoard() {
        return new IntelBoard(1234);
    }
}
