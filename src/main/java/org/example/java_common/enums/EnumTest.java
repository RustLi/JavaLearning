package java_common.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumTest {

    INSTANCE(1,"test");

    public Integer value;
    public String desc;

    EnumTest(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private static final Map<Integer, EnumTest> cache;

    static {
        cache = new HashMap<>();
        for (EnumTest e : EnumTest.values()) {
            cache.put(e.value, e);
        }
    }

    public static EnumTest getType(Integer value) {
        return cache.get(value);
    }


    public static void main(String[] args) {
        EnumTest enumTest = EnumTest.getType(2);
        System.out.println("enumTest = " + enumTest);
        switch (enumTest){
            case INSTANCE:
                System.out.println(2222);
                break;
            default:
                System.out.println(111);
                break;
        }
    }
}
