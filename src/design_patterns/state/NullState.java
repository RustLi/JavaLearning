package design_patterns.state;

/**
 * @program: javaProjects
 * @description: 状态空
 * @author: RustLi
 * @create: 2018-11-14 16:05
 **/
public class NullState implements State {

    @Override
    public void press() {
        System.out.println("null");
    }
}
