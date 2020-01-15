package test.retrofit_test;

public enum  RequestImpl1 implements IRequest{
    INSTANCE;

    @Override
    public void testRetrofit() {
        System.out.println("111");
    }
}
