package design_patterns.factory.simple_factory;

/**
 * @program: javaProjects
 * @description: intel board
 * @author: RustLi
 * @create: 2018-11-13 10:51
 **/
public class IntelBoard implements Board{
    private int intelBoard;
    IntelBoard(int type){
        intelBoard = type;
    }
    @Override
    public void installBoard() {
        System.out.println(intelBoard);
    }
}
