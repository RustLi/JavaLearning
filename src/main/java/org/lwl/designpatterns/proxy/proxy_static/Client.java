package org.lwl.designpatterns.proxy.proxy_static;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 14:07
 **/
public class Client {
    public static void main(String[] args) {
        //此处采用ProxyObject是关键
        AbstractObject mObj = new ProxyObject();
        mObj.operation();
    }
}
