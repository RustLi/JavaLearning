package org.lwl.designpatterns.chainofresponsibility;


public interface Ratify {
    //处理请求
    public Result deal(Chain chain);

    //封装Request和Result，转发责任链
    interface Chain{
        //获取当前Request
        Request request();

        //转发Request
        Result proceed(Request request);
    }
}
