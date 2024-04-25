package design_patterns.factory.AbstractFactory;

/**
 * @program: javaProjects
 * @description: amd 电脑工厂
 * @author: RustLi
 * @create: 2018-11-13 11:16
 **/
public class ADMFactory implements AbstractFactory {
    @Override
    public Cpu createCpu() {
        return new IntelCpu(222);
    }

    @Override
    public Board createBoard() {
        return new IntelBoard(123);
    }
}
