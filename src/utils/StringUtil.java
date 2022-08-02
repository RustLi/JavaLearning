package utils;

public class StringUtil {
    public static void main(String[] args) {
        String imgPath = "/sss/ssda/mars/111.jpg";
        String fileName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        System.out.println(fileName);
    }
}
