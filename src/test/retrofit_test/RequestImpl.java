package test.retrofit_test;

public class RequestImpl extends BaseRequest implements IRequest{
    @Override
    public void testRetrofit() {
        System.out.println("testRetrofit");
    }

    @Override
    public void test() {
        System.out.println("test");
    }

    public void test1(){
        System.out.println("1111");
    }
}
