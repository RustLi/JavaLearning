package design_patterns.factory.simple_factory;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-13 10:49
 **/
public class Client {
    public static void main(String[] args) {
        ComputerEngineer computerEngineer = new ComputerEngineer();
        computerEngineer.makeComputer(1,2);
    }
}
