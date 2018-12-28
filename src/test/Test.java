package test;

public class Test {
    public static void main(String[] args) {
        String name = DataBean.class.getName();
        System.out.println(name);


        System.out.println((int) Math.ceil((double) 0/20));
        System.out.println((int) Math.ceil((double) 5/20));
        System.out.println((int) Math.ceil((double) 20/20));
        System.out.println((int) Math.ceil((double) 35/20));
        System.out.println((int) Math.ceil((double) 21/20));

    }

    public static class DataBean{
        public  String name = "li" ;
        public  String sex = "male";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
