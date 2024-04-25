package design_patterns.combination;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: javaProjects
 * @description: 组合文件
 * @author: RustLi
 * @create: 2018-11-14 17:23
 **/
public class CombinationFile extends AbstractFile{

    private AbstractFile mAbstractFile;
    private List<AbstractFile> mFileList = new ArrayList<>();
    public void add(AbstractFile abstractFile){
        mFileList.add(abstractFile);
    }
    @Override
    protected void findFile() {
        for (AbstractFile mFile:mFileList) {
            mFile.findFile();
        }
    }
}
