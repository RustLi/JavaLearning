package test;

public class Test1 {

    public static class BaseInParam{
        public String targetId;
    }

    public static class InParam extends BaseInParam{
        public String content;

        public InParam(String targetId, String content) {
            this.targetId = targetId;
            this.content = content;
        }
    }




    public void doSomeThing(){
        System.out.println("doSomething");

        for (int i = 0; i < 3; i++) {
            System.out.println(iBase.hashCode());
        }
    }

    IBase iBase = new IBase() {
        @Override
        public void test() throws Exception {

        }
    };
}
