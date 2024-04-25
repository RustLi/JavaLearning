package test.callbacktest;

public class Client {
    public static void main(String[] args) {

        CallOne callOne = new CallOne();
        callOne.start();
        CallTwo callTwo = new CallTwo();
        callTwo.start();

//        Proxy.getInstance().startThread();
    }
}
