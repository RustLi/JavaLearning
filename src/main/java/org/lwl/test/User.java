package org.lwl.test;

import lombok.Data;

@Data
public class User {

    private String name;
    private Integer age;

    public void test(String test){
        if ("aa".equals(test)){
            throw new IllegalArgumentException("参数异常");
        }
    }
}
