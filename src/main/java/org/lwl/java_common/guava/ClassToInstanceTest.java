package java_common.guava;

import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import java.util.HashMap;
import java.util.Map;

public class ClassToInstanceTest {
    public static void main(String[] args) {
        test();
    }

    private static void test(){
        //常规Map
        Map<Class<?>,Object> map=new HashMap<>();
        User user=new User("A",18);
        Dept dept=new Dept("A","部门-1");
        map.put(User.class,user);
        map.put(Dept.class,dept);
//        map.put(User.class,dept);
        User aUser = (User) map.get(User.class);
        Dept aDept = (Dept)map.get(Dept.class);
        System.out.println(aUser + ":" + aDept);

        //MutableClassToInstanceMap
        MutableClassToInstanceMap<Object> instanceMap = MutableClassToInstanceMap.create();
        User user1=new User("A",20);
        Dept dept1=new Dept("A","部门-1");
        instanceMap.putInstance(User.class,user1);
        instanceMap.putInstance(Dept.class,dept1);
        User bUser = instanceMap.getInstance(User.class);
        Dept bDept = instanceMap.getInstance(Dept.class);
        System.out.println(bUser + ":" + bDept);

//        instanceMap.putInstance(User.class,dept1);

        ImmutableClassToInstanceMap<Number> imMap =
                new ImmutableClassToInstanceMap.Builder<Number>()
                        .put(Integer.class, 100)
                        .put(Float.class, 10.01f)
                        .build();
        ImmutableClassToInstanceMap<Number> imMap2 = ImmutableClassToInstanceMap.copyOf(imMap);
        // throws UnsupportedOperationException
        // map.putInstance(Integer.class, 1000);
        // map.put(Integer.class, 1000);
        System.out.println(imMap.getInstance(Integer.class));
        System.out.println(imMap2.getInstance(Float.class));

    }

    private static class User{
        public String name;
        public Integer age;

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    private static class Dept{
        public String name;
        public String deptName;

        public Dept(String name, String deptName) {
            this.name = name;
            this.deptName = deptName;
        }

        @Override
        public String toString() {
            return "Dept{" +
                    "name='" + name + '\'' +
                    ", deptName='" + deptName + '\'' +
                    '}';
        }
    }
}
