package org.lwl.designpatterns.decorator;

/**
 * @program: javaProjects
 * @description: 画矩形
 * @author: RustLi
 * @create: 2018-11-14 14:37
 **/
public class Rectangle implements Shape{
    @Override
    public void draw() {
        System.out.println("draw rect");
    }
}
