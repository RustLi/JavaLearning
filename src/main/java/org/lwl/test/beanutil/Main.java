package org.lwl.test.beanutil;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        person.name = "lwl";
        person.age = 11;
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        person.list = list;

        User user = new User();
        try {
            BeanUtils.copyProperties(person,user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user);
    }


    /**
     * 获取异常栈字符串
     *
     * @param e
     * @return
     */
    public static String getExp(Throwable e) {
        if (e == null) {
            return "";
        }
        StackTraceElement[] traceElements = e.getStackTrace();

        StringBuilder builder = new StringBuilder();
        builder.append(e.fillInStackTrace());

        for (StackTraceElement s : traceElements) {
            builder.append("\n\tat ").append(s);
        }

        return builder.toString();
    }
}
