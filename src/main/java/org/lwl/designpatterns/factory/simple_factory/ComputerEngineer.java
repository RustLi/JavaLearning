package org.lwl.designpatterns.factory.simple_factory;

/**
 * @program: javaProjects
 * @description: computer engineer
 * @author: RustLi
 * @create: 2018-11-13 10:52
 **/
public class ComputerEngineer {
    private Cpu cpu;
    private Board board;

    public void makeComputer(int cpuType,int boardType){
        cpu = CpuFactory.createCpu(cpuType);
        board = BoardFactory.createBoard(boardType);
        if (cpu != null) {
            cpu.installCpu();
        }
        if (board != null) {
            board.installBoard();
        }
    }
}
