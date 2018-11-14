package design_patterns.state;

/**
 * @program: javaProjects
 * @description: 状态满
 * @author: RustLi
 * @create: 2018-11-14 16:04
 **/
public class FullState implements State {
    @Override
    public void press() {
        System.out.println("full");
    }
}
