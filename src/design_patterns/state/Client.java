package design_patterns.state;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 15:53
 **/
public class Client {
    public static void main(String[] args) {
        StateImpl stateImpl = new StateImpl(new FullState());
        for (int i = 0; i < 20; i++) {
            stateImpl.press();
        }
    }
}
