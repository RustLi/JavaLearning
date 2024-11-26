package org.lwl.designpatterns.strategy;

import org.lwl.designpatterns.strategy.enums.ISyncAttachMsg;
import org.lwl.designpatterns.strategy.enums.MsgAttachType;
import org.lwl.designpatterns.strategy.enums.Type2Obj;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 15:48
 **/
public class Client {

    public static void main(String[] args) {
        //第一种，直接组合
        ContextImpl context = new ContextImpl(new ConcreteStrategyA());
        context.executeStrategy();

        //第二种，字符串匹配
        Deal deal = new Deal();
        deal.shareOptions("Sina");

        //第三种，枚举
        Object obj = Type2Obj.getObjByType(MsgAttachType.IMAGE.getValue());
        if(obj != null){
            ISyncAttachMsg iSyncAttachMsg = (ISyncAttachMsg) obj;
            iSyncAttachMsg.handleAttachFile(new Object());
        }

    }

}
