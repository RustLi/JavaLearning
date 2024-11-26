package org.lwl.designpatterns.factory.AbstractFactory;

/**
 * @program: javaProjects
 * @description: board factory
 * @author: RustLi
 * @create: 2018-11-13 10:57
 **/
public class BoardFactory {
    public static Board createBoard(int type){
        Board board = null;
        if (type == 1){
            board = new IntelBoard(211);
        }else if (type == 2){
            board = new AMDBoard(456);
        }
        return board;
    }
}
