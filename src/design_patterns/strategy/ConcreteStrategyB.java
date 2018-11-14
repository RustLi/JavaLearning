package design_patterns.strategy;

/**
 * @program: javaProjects
 * @description: 具体策略类B
 * @author: RustLi
 * @create: 2018-11-14 15:50
 **/
public class ConcreteStrategyB implements Strategy {
    @Override
    public void operation() {
        System.out.println("B");
    }
}
