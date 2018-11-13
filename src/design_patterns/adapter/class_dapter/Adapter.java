package design_patterns.adapter.class_dapter;

/**
 * @program: javaProjects
 * @description: 适配器，用于将ps和usb接口适配
 * @author: RustLi
 * @create: 2018-11-13 17:26
 **/
public class Adapter extends Usber implements Ps{
    @Override
    public void isPs() {
        isUsb();
    }
}
