package objectclone;

/**
 * Created by bryantchang on 2017/7/9.
 * 对象克隆
 * 要想实现clone,需要被克隆的对象所在类需要实现Cloneable接口,该接口只是一种标识接口,表示一种能力
 */
class Person implements Cloneable{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name=" + this.getName() + "age=" + this.getAge();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
public class TestDemo {
    public static void main(String[] args) {
        Person per1 = new Person("a", 10);
        try {
            Person per2 = (Person)per1.clone();
            System.out.println(per1);
            per2.setAge(20);
            System.out.println(per2);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }
}
