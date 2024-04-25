package org.lwl.test.enumTest;

public enum EnumTest {
    ONE(1,"a"),
    TWO(2,"b"),
    THREE(3,"c");

    private int id;
    private String name;
    EnumTest(int i, String b) {
        this.id =  i;
        this.name = b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
