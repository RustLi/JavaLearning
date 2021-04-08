package design_patterns.strategy;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 15:48
 **/
public class Client {
    public static void main(String[] args) {
        ContextImpl context = new ContextImpl(new ConcreteStrategyA());
        context.executeStrategy();

        Deal deal = new Deal();
        deal.shareOptions("Sina");
    }

}
