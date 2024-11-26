package org.lwl.designpatterns.bridge;

/**
 * @program: javaProjects
 * @description: 绿色的圆
 * @author: RustLi
 * @create: 2018-11-14 16:44
 **/
public class GreenCircle implements DrawCircle{
    @Override
    public void drawCircle() {
        System.out.println("green circle");
    }
}
