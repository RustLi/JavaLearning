package org.lwl.test.trans;

public class SubDto extends MainDto {
    private String subName;

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    @Override
    public String toString() {
        return "SubDto{" +
                "subName='" + subName + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
}
