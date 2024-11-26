package org.lwl.designpatterns.factory.AbstractFactory;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-13 10:49
 **/
public class Client {
    public static void main(String[] args) {
        ComputerEngineer computerEngineer = new ComputerEngineer();

        AbstractFactory abstractFactory = new IntelFactory();
        computerEngineer.makeComputer(abstractFactory);
    }
}
