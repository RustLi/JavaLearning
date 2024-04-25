package org.lwl.test;

//package org.example.test;

//父类静态变量初始化，不会触发子类的初始化
    class SuperClass{
        public static int value=123;
        static{
            System.out.println("SuperClass init!");
        }
    }

    class SubClass extends SuperClass{
        static{
            System.out.println("SubClass init!");
        }
    }

    public class ClassInitTest{

        public static void main(String[] args){
            System.out.println(SubClass.value);
            //output:
            // SuperClass init!
            //123
        }
    }



/*class ConstClass{
    public static final String HELLO_WORLD="hello world";
    static {
        System.out.println("ConstClass init!");
    }
}

public class ClassInitTest{
    public static void main(String[] args){
        System.out.print(ConstClass.HELLO_WORLD);//output:hello world
    }*/


//}

