package java_common.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectObject {

    public static void main(String[] args) {
        try {
            // 创建对象
            ReflectObject.reflectNewInstance();

            // 反射私有的构造方法
            ReflectObject.reflectPrivateConstructor();

            // 反射私有属性
            ReflectObject.reflectPrivateField();

            // 反射私有方法
            ReflectObject.reflectPrivateMethod();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // 创建对象
    private static void reflectNewInstance() {
        try {
            Class<?> classBook = Class.forName("java_common.reflect.Book");
            Object objectBook = classBook.newInstance();
            Book book = (Book) objectBook;
            book.setName("Android进阶之光");
            book.setAuthor("刘望舒");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 反射私有的构造方法
    private static void reflectPrivateConstructor() {
        try {
            Class<?> classBook = Class.forName("java_common.reflect.Book");
            Constructor<?> declaredConstructorBook = classBook.getDeclaredConstructor(String.class,String.class);
            declaredConstructorBook.setAccessible(true);
            Object objectBook = declaredConstructorBook.newInstance("Android开发艺术探索","任玉刚");
            Book book = (Book) objectBook;
            System.out.println("book name is " + book.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 反射私有属性
    private static void reflectPrivateField() {
        try {
            Class<?> classBook = Class.forName("java_common.reflect.Book");
            Object objectBook = classBook.newInstance();
            Field fieldTag = classBook.getDeclaredField("TAG");
            fieldTag.setAccessible(true);
            String tag = (String) fieldTag.get(objectBook);
            System.out.println("tag is " + tag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 反射私有方法
    private static void reflectPrivateMethod() {
        try {
            Class<?> classBook = Class.forName("java_common.reflect.Book");
            Method methodBook = classBook.getDeclaredMethod("declaredMethod",int.class);
            methodBook.setAccessible(true);
            Object objectBook = classBook.newInstance();
            String str = (String) methodBook.invoke(objectBook,0);
            System.out.println("str is " + str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}
