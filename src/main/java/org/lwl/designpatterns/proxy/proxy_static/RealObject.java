package org.lwl.designpatterns.proxy.proxy_static;

/**
 * @program: javaProjects
 * @description: 真实类
 * @author: RustLi
 * @create: 2018-11-14 14:09
 **/
public class RealObject extends AbstractObject {
    @Override
    public void operation() {
        System.out.println("do something");
    }
}
