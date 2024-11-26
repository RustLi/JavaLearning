package org.lwl.designpatterns.proxy.proxy_static;

/**
 * @program: javaProjects
 * @description: 代理类
 * @author: RustLi
 * @create: 2018-11-14 14:11
 **/
public class ProxyObject extends AbstractObject {
    RealObject mRealObject = new RealObject();
    @Override
    public void operation() {
        mRealObject.operation();
    }
}
