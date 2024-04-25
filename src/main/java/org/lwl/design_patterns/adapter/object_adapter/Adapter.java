package design_patterns.adapter.object_adapter;

/**
 * @program: javaProjects
 * @description: 适配器，用于将ps和usb接口适配
 * @author: RustLi
 * @create: 2018-11-13 17:26
 **/
public class Adapter extends Usber implements Ps {

    private Usber usber;
    //组合关系
    Adapter(Usber usber){
        this.usber = usber;
    }

    @Override
    public void isPs() {
        usber.isUsb();
    }
}
