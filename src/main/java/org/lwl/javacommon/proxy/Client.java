package org.lwl.javacommon.proxy;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        ProxyImpl proxy = new ProxyImpl();
        IProxy iProxy = (IProxy) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{IProxy.class},new ProxyHandler(proxy));
        String word = iProxy.sayHello();
        System.out.println(word);
    }
}
