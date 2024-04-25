package design_patterns.bridge;

/**
 * @program: javaProjects
 * @description: 抽象桥接类，持有接口的引用（构造器为聚合，set方法为关联）
 * @author: RustLi
 * @create: 2018-11-14 16:45
 **/
public abstract class AbstractBridge {
    public DrawCircle mDrawCircle;

    public AbstractBridge(DrawCircle drawCircle){
        mDrawCircle = drawCircle;
    }
    public abstract void draw();
}
