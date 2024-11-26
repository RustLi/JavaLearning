package org.lwl.designpatterns.bridge;

/**
 * @program: javaProjects
 * @description: 具体实现画圆
 * @author: RustLi
 * @create: 2018-11-14 16:48
 **/
public class Circle extends AbstractBridge {

    public Circle(DrawCircle drawCircle) {
        super(drawCircle);
    }

    @Override
    public void draw() {
        mDrawCircle.drawCircle();
    }
}
