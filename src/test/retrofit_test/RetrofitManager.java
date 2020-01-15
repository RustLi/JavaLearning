package test.retrofit_test;

public class RetrofitManager {

    private static IRequest USER_MANAGER_REQUEST = Retrofit.INSTANCE.create(new RequestImpl());

    public static IRequest getRequest(){
        return USER_MANAGER_REQUEST;
    }
}
