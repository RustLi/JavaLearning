package design_patterns.observer;

/**
 * @program: javaProjects
 * @description: 具体的观察者
 * @author: RustLi
 * @create: 2018-11-12 17:13
 **/
public class ConcreteObserver implements Observer{
    @Override
    public void doThing(String message) {
        System.out.println(message);
    }
}
