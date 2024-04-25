package org.lwl.test.retrofit_test;

public class RequestImpl extends BaseRequest implements IRequest{
    @Override
    public void testRetrofit(String str) {
        System.out.println("testRetrofit str = " + str);
    }

}
