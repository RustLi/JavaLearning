package org.lwl.designpatterns.strategy;

import java.util.ArrayList;
import java.util.List;

public class Deal {
    private static List<DealContext> algs = new ArrayList();
    //静态代码块,先加载所有的策略
    static {
        algs.add(new DealContext("Sina",new ConcreteStrategyA()));
        algs.add(new DealContext("WeChat",new ConcreteStrategyB()));
    }

    public void shareOptions(String type){
        DealStrategy dealStrategy = null;
        for (DealContext deal : algs) {
            if (deal.options(type)) {
                dealStrategy = deal.getDeal();
                break;
            }
        }
        if (dealStrategy != null){
            dealStrategy.dealMethod(type);
        }
    }
}
