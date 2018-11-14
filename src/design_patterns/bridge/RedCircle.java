package design_patterns.bridge;

/**
 * @program: javaProjects
 * @description: 红色的圆
 * @author: RustLi
 * @create: 2018-11-14 16:43
 **/
public class RedCircle implements DrawCircle {
    @Override
    public void drawCircle() {
        System.out.println("red circle");
    }
}
