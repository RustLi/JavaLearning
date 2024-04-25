package design_patterns.factory.simple_factory;

/**
 * @program: javaProjects
 * @description: amd board
 * @author: RustLi
 * @create: 2018-11-13 10:51
 **/
public class AMDBoard implements Board{

    private int boardType;
    AMDBoard(int type){
        boardType = type;
    }

    @Override
    public void installBoard() {
        System.out.println(boardType);
    }
}
