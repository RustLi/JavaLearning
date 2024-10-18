package java_common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
    private IProxy mProxy;
    public ProxyHandler(IProxy proxy) {
        this.mProxy = proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke before...");
//        return "hhh";
        return method.invoke(mProxy,args);
    }
}
