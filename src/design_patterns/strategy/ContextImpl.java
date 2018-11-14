package design_patterns.strategy;

/**
 * @program: javaProjects
 * @description: 策略包装类
 * @author: RustLi
 * @create: 2018-11-14 15:51
 **/
public class ContextImpl  {
    private Strategy mStrategy;

    public ContextImpl(Strategy strategy){
        mStrategy = strategy;
    }

    public void executeStrategy(){
        mStrategy.operation();
    }
}
