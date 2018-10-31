package annotation;

/**
 * @program: javaProjects
 * @description: 用户信息
 * @author: RustLi
 * @create: 2018-10-31 10:45
 **/
@Table("user")
public class User {
    @Column("id")
    private int id;
    @Column("userName")
    private String userName;
    @Column("nickName")
    private String nickName;
    @Column("age")
    private int age;
    @Column("city")
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
