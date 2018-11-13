package design_patterns.adapter.interface_adapter;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-13 17:32
 **/
public class Client {
    public static void main(String[] args) {
        A a = new ConcreteA();
        a.a();
        a.b();
        a.c();
    }
}
