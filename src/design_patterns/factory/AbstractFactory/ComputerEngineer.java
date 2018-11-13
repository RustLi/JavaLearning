package design_patterns.factory.AbstractFactory;

/**
 * @program: javaProjects
 * @description: computer engineer
 * @author: RustLi
 * @create: 2018-11-13 10:52
 **/
public class ComputerEngineer {

    private Cpu cpu;
    private Board board;
    public void makeComputer(AbstractFactory abstractFactory){
        cpu = abstractFactory.createCpu();
        board = abstractFactory.createBoard();

        if (cpu != null) {
            cpu.installCpu();
        }
        if (board != null) {
            board.installBoard();
        }
    }
}
