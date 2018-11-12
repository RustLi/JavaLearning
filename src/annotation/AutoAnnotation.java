package annotation;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @program: javaProjects
 * @description: 自定义注解，检索表中的数据
 * @author: RustLi
 * @create: 2018-10-31 10:23
 **/
public class AutoAnnotation {
    public static void main(String[] args){
        User user1 = new User();
        user1.setId(10);

        User user2 = new User();
        user2.setUserName("jack");

        User user3 = new User();
        user3.setCity("wuhan");

        AutoAnnotation autoAnnotation = new AutoAnnotation();

        String sql1 = autoAnnotation.query(user1);
        String sql2 = autoAnnotation.query(user2);
        String sql3 = autoAnnotation.query(user3);
        System.out.println(sql1);
        System.out.println("-------");
        System.out.println(sql2);
        System.out.println("-------");
        System.out.println(sql3);
    }

    private String query(User user){
        StringBuilder stringBuilder = new StringBuilder();
        Class c = user.getClass();
        boolean isClassExist = c.isAnnotationPresent(Table.class);
//        System.out.println(isClassExist);
        if (!isClassExist){
            return null;
        }
        Table table = (Table)c.getAnnotation(Table.class);
        String tableName = table.value();
        stringBuilder.append("select * from ").append(tableName).append(" where 1=1 ");
//        System.out.println(stringBuilder);

        Field[] fields = c.getDeclaredFields();
        for (Field field:fields){
            boolean isFieldExist = field.isAnnotationPresent(Column.class);
            if (!isFieldExist){
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            String fieldName = column.value();
//            System.out.println(fieldName);
            String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
//            System.out.println(getMethodName);
            Object fieldValue = null;
            Method getMethod = null;
            try {
                getMethod = c.getMethod(getMethodName);
                fieldValue = getMethod.invoke(user);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
            stringBuilder.append(" and ").append(fieldName).append(" = ").append(fieldValue);
        }
        return stringBuilder.toString();
    }
}
