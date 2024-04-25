package org.lwl.test.retrofit_test;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Retrofit {
    @SuppressWarnings("unchecked")
    public static  <T> T create(@NotNull  final Object service) {
        return (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override public Object invoke(Object proxy, Method method, Object... args)
                            throws Throwable {
                        System.out.println(method.getDeclaringClass());
                        // If the method is a method from Object then defer to normal invocation.
//                        if (method.getDeclaringClass() != Object.class) {
//                            throw new IllegalArgumentException();
//                        }
//                        System.out.println(method.getDeclaringClass());
                        return method.invoke(service, args);
                    }
                });
    }


//    @SuppressWarnings("unchecked")
//    public static <T> T create1(final Class<T> service) {
//        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
//                new InvocationHandler() {
//                    @Override public Object invoke(Object proxy, Method method, Object... args)
//                            throws Throwable {
//                        // If the method is a method from Object then defer to normal invocation.
////                        if (method.getDeclaringClass() != Object.class) {
////                            throw new IllegalArgumentException();
////                        }
////                        System.out.println(method.getDeclaringClass());
//                        return method.invoke(service, args);
////                        return null;
//                    }
//                });
//    }
}
