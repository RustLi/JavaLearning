package design_patterns.combination;

/**
 * @program: javaProjects
 * @description: txt文件
 * @author: RustLi
 * @create: 2018-11-14 17:22
 **/
public class TxtFile extends AbstractFile {
    @Override
    protected void findFile() {
        System.out.println("find txt");
    }
}
