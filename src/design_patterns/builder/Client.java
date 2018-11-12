package design_patterns.builder;

/**
 * @program: javaProjects
 * @description: 测试类
 * @author: RustLi
 * @create: 2018-11-12 14:37
 **/
public class Client {
    public static void main(String[] args) {
        Director director = new Director();
        AbstractBuilder concreteBuilder = new ConcreteBuilder();
        director.setAbstractBuilder(concreteBuilder);
        director.constructCar();

        director.getCar();

        //链式调用
        Room room = new Room.Builder().color("red").size(3).build();
    }
}
