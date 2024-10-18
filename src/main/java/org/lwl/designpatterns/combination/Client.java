package design_patterns.combination;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 17:19
 **/
public class Client {
    public static void main(String[] args) {
        CombinationFile combinationFile = new CombinationFile();
        combinationFile.add(new ImageFile());
        combinationFile.add(new TxtFile());
        combinationFile.add(new VideoFile());
        combinationFile.findFile();
    }
}
