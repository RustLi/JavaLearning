package design_patterns.proxy.proxy_dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: javaProjects
 * @description: 动态代理处理器对象
 * @author: RustLi
 * @create: 2018-11-14 14:17
 **/
public class MyInvocationHandler implements InvocationHandler {

    Subject mRealObject;

    public MyInvocationHandler(Subject realObject){
        mRealObject = realObject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("operation")){
            method.invoke(mRealObject,args);
            return null;
        }
        //该返回值为invoke()方法调用的方法的返回值
        return null;
    }
}
