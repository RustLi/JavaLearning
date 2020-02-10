package test.retrofit_test;

public class RetrofitManager {

//    private static IRequest USER_MANAGER_REQUEST = Retrofit.INSTANCE.create(RequestImpl1.INSTANCE);

    private static IRequest USER_MANAGER_REQUEST = Retrofit.create(new RequestImpl());

//    private static IRequest USER_MANAGER_REQUEST = new RequestImpl();

    public static IRequest getRequest(){
        return USER_MANAGER_REQUEST;
    }
}
