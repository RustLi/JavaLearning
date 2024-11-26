package org.lwl.designpatterns.flyweight;

/**
 * @program: javaProjects
 * @description: 画圆
 * @author: RustLi
 * @create: 2018-11-14 17:47
 **/
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("draw circle");
    }
}
