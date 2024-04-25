package org.lwl.test.retrofit_test;

public class RetrofitTestClient {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        RetrofitManager.getRequest().testRetrofit("lwl");
        long time1 = System.currentTimeMillis() -time;
        System.out.println("lwl 动态代理耗时： " + time1);
//        RetrofitManager.getRequest().test();
        //报错，无法向下转型，动态代理可控制对对象的访问，只能通过接口访问
//        RequestImpl request = (RequestImpl)RetrofitManager.getRequest();
//        request.test1();

//        IRequest iRequest = Retrofit.INSTANCE.create1(IRequest.class);
//        iRequest.toString();

//        IRequest iRequest1 = retrofit.create1(IRequest.class);
//        new RequestImpl().testRetrofit();
    }
}
