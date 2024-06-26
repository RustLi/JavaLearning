package org.lwl.test;

import java.util.Optional;


public class OptionTest {

    public static void main(String[] args) {
//        orElseGet();
        orElse();
    }

    /**
     * ofNullable有值的话orELseGet中的代码不会执行
     **/
    private static void orElseGet(){
        Optional<Integer> optional1 = Optional.ofNullable(1);
        Optional<Integer> optional2 = Optional.ofNullable(null);

        int result1 = optional1.orElseGet(() -> {
            // 生成默认值的代码
            return 0;
        });

        int result2 = optional2.orElseGet(() -> {
            // 生成默认值的代码
            return 0;
        });

        System.out.println(result1); // 输出 1
        System.out.println(result2); // 输出 0
    }

    private static void orElse(){
        Optional<Integer> optional1 = Optional.ofNullable(1);
        Optional<Integer> optional2 = Optional.ofNullable(null);

        int result1 = optional1.orElse(0);

        int result2 = optional2.orElse(0);

        System.out.println(result1); // 输出 1
        System.out.println(result2); // 输出 0

    }

    private static void test(){
        User user = new User("ccc",111,null);
//        User user = null;

        //user非null时 orElse还是会走
//        Optional.ofNullable(user).orElse(print());
        //user非null时 orElseGet不会走
//        Optional.ofNullable(user).orElseGet(() -> print());

//        String name = Optional.ofNullable(user)
//                .map(u -> u.getName()).orElse("nnnn");

//        String name = Optional.ofNullable(user)
//                .flatMap(User::getPosition).orElse("sss");

//        Optional<User> emptyOpt = Optional.ofNullable(user);
//        System.out.println(emptyOpt);
//        System.out.println(emptyOpt.get());

//        Optional.ofNullable(user).ifPresent(e -> System.out.println(e.getName()));
//        System.out.println(name);


    }

    private static void testChain(){
        User user = new User("ccc",111,null);
//        User user = null;

        System.out.println("user = " + user);
        if (user != null){
            Address address = user.address;
            System.out.println("address = " + address);
            if (address != null){
                String provice = address.province;
                System.out.println(provice);
            }
        }

        String ret = Optional.ofNullable(user)
                .flatMap(u -> u.getAddress())
                .flatMap(c -> c.getProvince())
                .orElse("default");
        System.out.println("ret = " + ret);
    }

    private static User print(){
        System.out.println("print");
        return new User("aaa",222);
    }

    private static class User{
        public String name;
        public int age;
        public Address address;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public User(String name, int age, Address address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public Optional<String> getPosition() {
            return Optional.ofNullable(name);
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    private static class Address{
        public String province;
        public String city;

        public Optional<String> getProvince() {
            return Optional.ofNullable(province);
        }

        public String getCity() {
            return city;
        }
    }
}
