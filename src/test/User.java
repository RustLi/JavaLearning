package test;

public class User {
    public void test(String test){
        if ("aa".equals(test)){
            throw new IllegalArgumentException("参数异常");
        }
    }
}
