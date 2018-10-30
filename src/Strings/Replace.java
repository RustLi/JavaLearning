package Strings;

public class Replace {
    public static void main(String[] args){
        String str = "we are happy. lala la";
        Replace replace = new Replace();
        String output = replace.replaceBlank(str);
        System.out.println(output);
    }

    private String replaceBlank(String intput){
        if (intput == null || intput.length() == 0){
            return null;
        }
        int originLength = intput.length();
        char[] charArray = intput.toCharArray();
        int blankNum = 0;
        //计算空格的数量，给新字符数组定长度
        for (int i = 0; i < charArray.length; i++){
            if (charArray[i] == ' '){
                blankNum++;
            }
        }
        int newLength = originLength + blankNum * 2;
        char[] newCharArrary = new char[newLength];

        int indexOrigin = 0;
        int indexNew = 0;
        while (indexOrigin < originLength){
            if (charArray[indexOrigin] == ' '){
                newCharArrary[indexNew++] = '%';
                newCharArrary[indexNew++] = '2';
                newCharArrary[indexNew++] = '0';
                indexOrigin++;
            }else {
                newCharArrary[indexNew++] = charArray[indexOrigin++];
            }
        }
        return String.valueOf(newCharArrary);
    }
}
