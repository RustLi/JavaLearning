package test;

public class InnerClassTest {
    public void test(){
        System.out.println(aaa.hashCode());
    }

    private InnerClassTest2 aaa = new InnerClassTest2();
}
