package comparator;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by bryantchang on 2017/8/6.
 */

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
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
}

class PersonComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o1.getAge() - o2.getAge();
    }
}

public class TestDemo {
    public static void main(String[] args) {
//        Person pers[] = new Person[] {
//                new Person("aaa", 10),
//                new Person("bbb", 20),
//                new Person("ccc", 30),
//        };
//        Arrays.sort(pers);
//        System.out.println(Arrays.toString(pers));
    }
}
