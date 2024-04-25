package design_patterns.prototype;

/**
 * @program: javaProjects
 * @description: 具体实现类
 * @author: RustLi
 * @create: 2018-11-14 13:15
 **/
public class WordDocument implements Cloneable {

    private String text;
    private String name;

    @Override
    protected WordDocument clone(){
        try {
            WordDocument document = (WordDocument)super.clone();
            document.text = this.text;
            document.name = this.name;
            return document;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
