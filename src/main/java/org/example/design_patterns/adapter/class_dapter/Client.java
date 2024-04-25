package design_patterns.adapter.class_dapter;

/**
 * @program: javaProjects
 * @description: 客户端，usb和ps2的转接
 * @author: RustLi
 * @create: 2018-11-13 17:18
 **/
public class Client {
    public static void main(String[] args) {
        Ps ps = new Adapter();
        //此处采用类适配器，将ps转换成了usb
        ps.isPs();
    }
}
