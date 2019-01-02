package test;

public class LambdaTest {
    public static void main(String[] args) {
        LambdaTest lambdaTest= new LambdaTest();
        MathOperation add = (int a,int b) -> a+b;
        MathOperation sub = (a,b) -> a-b;
        MathOperation multiplication = (int a,int b) -> {return a*b;};
        MathOperation division = (int a,int b) -> a/b;
        System.out.println(lambdaTest.operate(10,5,add));
        System.out.println(lambdaTest.operate(10,5,sub));
        System.out.println(lambdaTest.operate(10,5,multiplication));
        System.out.println(lambdaTest.operate(10,5,division));

        GreetingService greetingService = message ->
                System.out.println("hello" + message);
        greetingService.sayMessage("world llalal");
    }

    interface MathOperation{
        int operation(int a,int b);
    }

    interface GreetingService{
        void sayMessage(String message);
    }

    private int operate(int a,int b,MathOperation mathOperation){
        return mathOperation.operation(a,b);
    }
}
