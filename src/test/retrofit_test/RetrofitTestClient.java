package test.retrofit_test;

public class RetrofitTestClient {

    public static void main(String[] args) {
        RetrofitManager.getRequest().testRetrofit();

//        IRequest iRequest = Retrofit.INSTANCE.create1(IRequest.class);
//        iRequest.testRetrofit();

//        IRequest iRequest1 = retrofit.create1(IRequest.class);
//        new RequestImpl().testRetrofit();
    }
}
