package strings;

public class ReplaceBlank {

    /**
     * 题目要求：
     * 实现一个函数，把字符串中的每个空格都替换成“%20”，已知原位置后面有足够的空余位置，要求改替换过程发生在原来的位置上。
     */
    public static void main(String[] args) {
        String str = "we are happy. lala la";
        ReplaceBlank replaceBlank = new ReplaceBlank();
        String output = replaceBlank.replaceBlank(str);
//        System.out.println(output);

        char[] predata = "We are happy.".toCharArray();
        char[] data = new char[20];
        for(int i=0;i<predata.length;i++)
            data[i] = predata[i];
        System.out.println(data);
        replaceBlank(data,13);
        System.out.println(data);
    }
    private String replaceBlank(String intput){
        if (intput == null || intput.length() == 0) {
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

    //由于java的字符数组没有结束符，所以需要多传入个原始长度
    //先计算好替换后的位置，从后向前替换，时间复杂度o(n)
    public  static void replaceBlank(char[] data,int length){
        int newLength = length;
        for(int i=0;i<length;i++){
            if(data[i]==' ')
                newLength += 2;
        }
        for(int indexOfOld = length-1,indexOfNew=newLength-1;indexOfOld>=0 && indexOfOld!=indexOfNew;indexOfOld--,indexOfNew--){
            if(data[indexOfOld]==' '){
                data[indexOfNew--] = '0';
                data[indexOfNew--] = '2';
                data[indexOfNew] = '%';
            }
            else{
                data[indexOfNew] = data[indexOfOld];
            }
        }
    }
}
