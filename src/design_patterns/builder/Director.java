package design_patterns.builder;

/**
 * @program: javaProjects
 * @description: 执行类
 * @author: RustLi
 * @create: 2018-11-12 16:09
 **/
public class Director {
   private AbstractBuilder abstractBuilder;

    public AbstractBuilder getAbstractBuilder() {
        return abstractBuilder;
    }

    public void setAbstractBuilder(AbstractBuilder abstractBuilder) {
        this.abstractBuilder = abstractBuilder;
    }

    public Car getCar(){
        return abstractBuilder.getCar();
    }

    public void constructCar(){
        abstractBuilder.createCar();
        abstractBuilder.buildColor();
        abstractBuilder.buildSize();
    }
}
