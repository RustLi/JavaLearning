package org.lwl.designpatterns.state;

/**
 * @program: javaProjects
 * @description: 状态转换类
 * @author: RustLi
 * @create: 2018-11-14 16:06
 **/
public class StateImpl {
    public static  int capacity = 10;
    private static State mState;
    public StateImpl(State state){
        mState = state;
    }

    private static void setState(State state){
        mState = state;
    }

    //此处实现状态间的转换
    public void press(){
        capacity--;
        if (capacity <= 0){
            setState(new NullState());
        }
        mState.press();
    }
}
