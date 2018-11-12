package design_patterns.builder;

import javafx.util.Builder;

/**
 * @program: javaProjects
 * @description: 链式调用的builder
 * @author: RustLi
 * @create: 2018-11-12 16:16
 **/
public class Room {
    private String color;
    private int size;
    private Builder builder;

    private Room(Builder builder){
        this.color = builder.color;
        this.size = builder.size;
    }

    public static class Builder{
        private String color;
        private int size;

        public Room build(){
            return new Room(this);
        }

        public Builder color(String color){
            this.color = color;
            return this;
        }

        public Builder size(int size){
            this.size = size;
            return this;
        }
    }
}
