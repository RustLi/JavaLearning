package test.retrofit_test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public enum Retrofit {

    INSTANCE;

    @SuppressWarnings("unchecked")
    public <T> T create(final Object service) {
        return (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override public Object invoke(Object proxy, Method method, Object... args)
                            throws Throwable {
                        return method.invoke(service, args);
                    }
                });
    }


    @SuppressWarnings("unchecked")
    public <T> T create1(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
                new InvocationHandler() {
                    @Override public Object invoke(Object proxy, Method method, Object... args)
                            throws Throwable {
                        // If the method is a method from Object then defer to normal invocation.
//                        if (method.getDeclaringClass() != Object.class) {
//                            throw new IllegalArgumentException();
//                        }
                        return method.invoke(service, args);
                    }
                });
    }
}
