package design_patterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaProjects
 * @description: 具体的被观察者
 * @author: RustLi
 * @create: 2018-11-12 17:12
 **/
public class ConcreteSubject implements Subject {
    private List<Observer> observerList = new ArrayList<>();


    @Override
    public void attach(Observer observer) {
        observerList.add(observer);

    }

    @Override
    public void detach(Observer observer) {
        observerList.remove(observer);
    }

    public void notifyObservers(String message){
        for (Observer observer: observerList) {
            observer.doThing(message);
        }
    }
}
