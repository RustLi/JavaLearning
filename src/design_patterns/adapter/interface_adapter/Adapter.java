package design_patterns.adapter.interface_adapter;

/**
 * @program: javaProjects
 * @description: 抽象类
 * @author: RustLi
 * @create: 2018-11-13 17:34
 **/
public abstract class Adapter implements A {
    @Override
    public void a() {
        System.out.println("a");
    }

    @Override
    public void b() {
        System.out.println("b");
    }

    @Override
    public void c() {
        System.out.println("c");
    }

    @Override
    public void d() {
        System.out.println("d");
    }

    @Override
    public void e() {
        System.out.println("e");
    }

    @Override
    public void f() {
        System.out.println("f");
    }

    @Override
    public void g() {
        System.out.println("g");
    }
}
