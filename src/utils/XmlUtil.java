package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;

public class XmlUtil {

    //<?xml version="1.0" encoding="UTF-8"?>
    //<beans xmlns="http://www.springframework.org/schema/beans">
    //
    //    <bean name="HelloWorld" class="com.xxp.HelloWord">
    //        <property name="first" value="Gob bless you!"></property>
    //        <property name="second" value="ComeBaby!"></property>
    //    </bean>
    //
    //</beans>

    public static String parseXml(String path) throws Exception{

        //1-获取XML-IO流：把要解析的 XML 文档转化为输入流，以便 DOM 解析器解析它
        InputStream xmlIns = new FileInputStream(path);

        //2-解析XML-IO流，获取 Document 对象，以及Document对象的根节点
        Element rootElement = getRootElementFromIs(xmlIns);
        //3~5-从根元素解析得到元素
       return parseElementFromRoot(rootElement);
    }

    //2-解析XML-IO流 ，获取Document 对象，以及Document对象 的根节点
    private static Element getRootElementFromIs(InputStream ins) throws Exception {
        if (ins == null) {
            return null;
        }
        /*
         * javax.xml.parsers 包中的DocumentBuilderFactory用于创建DOM模式的解析器对象 ，
         * DocumentBuilderFactory是一个抽象工厂类，它不能直接实例化，但该类提供了一个newInstance方法 ，
         * 这个方法会根据本地平台默认安装的解析器，自动创建一个工厂的对象并返回。
         */
        //a-调用 DocumentBuilderFactory.newInstance() 方法得到创建 DOM 解析器的工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //b-调用工厂对象的 newDocumentBuilder方法得到 DOM 解析器对象。
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        //c-调用 DOM 解析器对象的 parse() 方法解析 XML 文档，得到代表整个文档的 Document 对象，进行可以利用DOM特性对整个XML文档进行操作了。
        Document doc = docBuilder.parse(ins);
        //d-得到 XML 文档的根节点
        Element root = doc.getDocumentElement();
        //e-关闭流
        if (ins != null) {
            ins.close();
        }
        return root;
    }

    //3-从根元素解析得到元素
    private static String parseElementFromRoot(Element root) {
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                //4-从元素解析得到属性值
                String name = ele.getAttribute("name");//根据属性名称读取属性值
                System.out.println("name：" + name);
                String className = ele.getAttribute("value");
                System.out.println("className：" + className);
                return className;
                //5-从元素解析特定子元素并解析(以property为例)
//                getCertainElementFromParentElement(ele);
            }
        }
        return "";
    }

    //5-从元素解析特定子元素并解析(以property为例)
    private static void getCertainElementFromParentElement(Element ele) {
        NodeList propertyEleList = ele.getElementsByTagName("int");//根据标签名称获取标签元素列表
        for (int i = 0; i < propertyEleList.getLength(); i++) {
            Node node = propertyEleList.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                System.out.println("propertyEle: name == " + name);
                String value = propertyEle.getAttribute("value");
                System.out.println("propertyEle: value == " + value);
            }
        }
    }
}
