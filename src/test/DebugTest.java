package test;

public class DebugTest {

    public static void main(String[] args) {
        changeVarValue();
    }

    /**
     * 设置断点条件
     **/
    private static void setDebugCondition(){
        for (int i = 0; i < 10; i++) {
            //设置i==5打印
            System.out.println(i);
        }
    }

    /**
     * 动态修改变量的值
     **/
    private static void changeVarValue(){
        int num = add(1,2);
        System.out.println(num);
    }

    private static int add(int i, int j){
        return i+j;
    }
}
