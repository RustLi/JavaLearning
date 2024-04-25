package org.lwl.test.callbacktest;

public class CallOne {
    public void start(){
        Proxy.getInstance().register(new ICallback() {
            @Override
            public void onCall(String code) {
                System.out.println("CallOne code = " + code);
            }
        });
    }
}
