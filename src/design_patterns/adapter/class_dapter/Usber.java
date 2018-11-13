package design_patterns.adapter.class_dapter;

/**
 * @program: javaProjects
 * @description: usb实现类
 * @author: RustLi
 * @create: 2018-11-13 17:23
 **/
public class Usber implements Usb {
    @Override
    public void isUsb() {
        System.out.println("is usb");
    }
}
