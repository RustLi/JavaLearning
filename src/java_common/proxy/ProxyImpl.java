package java_common.proxy;

public class ProxyImpl implements IProxy {
    @Override
    public String sayHello() {
        System.out.println("123.....");
        return "123";
    }
}
