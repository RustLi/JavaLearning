package design_patterns.combination;

/**
 * @program: javaProjects
 * @description: 图像文件
 * @author: RustLi
 * @create: 2018-11-14 17:21
 **/
public class ImageFile extends AbstractFile {
    @Override
    protected void findFile() {
        System.out.println("find image");
    }
}
