package design_patterns.decorator;

/**
 * @program: javaProjects
 * @description: 具体的装饰类
 * @author: RustLi
 * @create: 2018-11-14 14:45
 **/
public class RedShapeDecorator extends AbstractShapeDecorator {

    //构造函数持有接口对象
    public RedShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRedBorder(decoratedShape);
    }

    private void setRedBorder(Shape shape){
        System.out.println("red board");
    }
}
