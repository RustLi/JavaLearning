package org.lwl.designpatterns.flyweight;

import java.util.HashMap;

/**
 * @program: javaProjects
 * @description: 工厂类
 * @author: RustLi
 * @create: 2018-11-14 17:45
 **/
public class CircleFactory {
    private static HashMap<String,Shape> sShapeHashMap = new HashMap<>();

    public static Circle getCircle(String color){
        Circle circle = (Circle)sShapeHashMap.get(color);
        if (circle == null) {
            circle = new Circle();
            sShapeHashMap.put(color,circle);
        }
        return circle;
    }
}
