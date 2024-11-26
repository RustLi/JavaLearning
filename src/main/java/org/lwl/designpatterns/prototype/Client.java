package org.lwl.designpatterns.prototype;

/**
 * @program: javaProjects
 * @description: 客户端
 * @author: RustLi
 * @create: 2018-11-14 13:14
 **/
public class Client {
    public static void main(String[] args) {
        WordDocument wordDocument = new WordDocument();
        wordDocument.setName("RustLi");
        wordDocument.setText("ProtoType origin");

        WordDocument wordDocument1 = wordDocument.clone();
        System.out.println(wordDocument1.getText());
        System.out.println(wordDocument1.getName());
    }
}
