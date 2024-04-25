package design_patterns.observer;

/**
 * @program: javaProjects
 * @description: 客户端调用
 * @author: RustLi
 * @create: 2018-11-12 17:14
 **/
public class Client {


    public static void main(String[] args) {
        Subject concreteSubject = new ConcreteSubject();
        Observer concreteObserver = new ConcreteObserver();

        concreteSubject.attach(concreteObserver);
        concreteSubject.notifyObservers("new message llll");
    }


}
