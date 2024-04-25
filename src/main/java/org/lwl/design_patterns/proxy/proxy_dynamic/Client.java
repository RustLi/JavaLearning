package design_patterns.proxy.proxy_dynamic;

import java.lang.reflect.Proxy;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 14:16
 **/
public class Client {
    public static void main(String[] args) {
        //真实对象
        Subject realObject = new RealObject();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(realObject);

        //代理对象
        Subject proxyClass = (Subject) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{Subject.class},myInvocationHandler);
        proxyClass.operation();
    }
}
