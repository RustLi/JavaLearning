package org.lwl.designpatterns.observer;

/**
 * @program: javaProjects
 * @description: 抽象被观察者
 * @author: RustLi
 * @create: 2018-11-12 17:11
 **/
public interface Subject {
    public void attach(Observer observer);

    public void detach(Observer observer);

    public void notifyObservers(String message);

}
