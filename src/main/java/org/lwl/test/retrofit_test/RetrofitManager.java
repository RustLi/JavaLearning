package org.lwl.test.retrofit_test;

public class RetrofitManager {

//    private static IRequest USER_MANAGER_REQUEST = Retrofit.INSTANCE.create(RequestImpl1.INSTANCE);

    private static IRequest iRequest;

    static {
        long time = System.currentTimeMillis();
        iRequest = new RequestImpl();
        long time1 = System.currentTimeMillis() -time;
        System.out.println("lwl 创建对象耗时 = " + time1);
    }
//    private static IRequest USER_MANAGER_REQUEST = Retrofit.create(iRequest);
    private static IRequest USER_MANAGER_REQUEST = iRequest;

//    private static IRequest USER_MANAGER_REQUEST = new RequestImpl();

    public static IRequest getRequest(){
        return USER_MANAGER_REQUEST;
    }
}
