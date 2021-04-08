package test.callbacktest;

public class CallTwo {
    public void start(){
        Proxy.getInstance().register(new ICallback() {
            @Override
            public void onCall(String code) {
                System.out.println("CallTwo code = " + code);
            }
        });
    }
}
