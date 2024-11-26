package org.lwl.designpatterns.facade;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 13:44
 **/
public class Client {
    public static void main(String[] args) {
        TvController mTvController = new TvController();
        mTvController.turnUp();
        mTvController.powerOn();
        mTvController.nextChannel();
    }
}
