package org.lwl.designpatterns.bridge;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 16:42
 **/
public class Client {
    public static void main(String[] args) {
        AbstractBridge redCircle = new Circle(new RedCircle());
        redCircle.draw();

        AbstractBridge greenCircle = new Circle(new GreenCircle());
        greenCircle.draw();
    }
}
