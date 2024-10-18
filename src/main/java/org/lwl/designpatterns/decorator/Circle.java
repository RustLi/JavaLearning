package design_patterns.decorator;

/**
 * @program: javaProjects
 * @description: 画圆形
 * @author: RustLi
 * @create: 2018-11-14 14:38
 **/
public class Circle implements Shape{
    @Override
    public void draw() {
        System.out.println("draw circle");
    }
}
