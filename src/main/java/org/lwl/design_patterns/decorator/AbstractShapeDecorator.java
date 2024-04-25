package design_patterns.decorator;

/**
 * @program: javaProjects
 * @description: 抽象装饰类
 * @author: RustLi
 * @create: 2018-11-14 14:40
 **/
public abstract class AbstractShapeDecorator implements Shape{
    protected Shape decoratedShape;
    public AbstractShapeDecorator(Shape shape){
        decoratedShape = shape;
    }

    public void draw(){
        decoratedShape.draw();
    }
}
