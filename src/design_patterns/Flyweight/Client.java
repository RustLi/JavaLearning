package design_patterns.Flyweight;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 17:45
 **/
public class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Circle circle = CircleFactory.getCircle("red");
            circle.draw();
        }
    }
}
