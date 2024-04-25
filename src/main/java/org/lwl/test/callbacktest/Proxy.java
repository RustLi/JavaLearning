package org.lwl.test.callbacktest;

public class Proxy {
    private Proxy() {}
    private static class SingletonInstance {
        private static final Proxy INSTANCE = new Proxy();
    }
    public static Proxy getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public int count = 0;
    public void register(ICallback iCallback){
        System.out.println("register");
        while (count <= 110){
            if (count == 110){
                System.out.println("111 count = " + count);
                iCallback.onCall("100");
            }
            System.out.println("run count 222 = " + count);
            count++;
        }
    }

    public void startThread(){
        System.out.println("startThread");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 110){
                    System.out.println("count = " + count);
                    count++;
                }
            }
        }).start();

    }
}
