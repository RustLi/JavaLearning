package org.lwl.test;


@lombok.Data
public class Data {
    private int id;
    private int count;

    public Data(int id, int count) {
        this.id = id;
        this.count = count;
    }
}
