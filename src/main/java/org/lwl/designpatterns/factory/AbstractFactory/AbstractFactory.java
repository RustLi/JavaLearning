package org.lwl.designpatterns.factory.AbstractFactory;

/**
 * @program: javaProjects
 * @description: 抽象电脑工厂
 * @author: RustLi
 * @create: 2018-11-13 11:14
 **/
public interface AbstractFactory {
     Cpu createCpu();
     Board createBoard();
}
