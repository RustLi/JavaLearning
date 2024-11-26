package org.lwl.designpatterns.builder;

/**
 * @program: javaProjects
 * @description: 建造者数据实体
 * @author: RustLi
 * @create: 2018-11-12 14:37
 **/
public class Car {
    private String color;
    private int size;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
