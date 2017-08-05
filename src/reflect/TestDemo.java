package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by bryantchang on 2017/8/5.
 */

class Person {
    private String name;

}

class Student extends Person{

}

public class TestDemo {
    public static void main(String[] args) throws Exception {
        String attribute = "name";
        String value = "MLDN";
        Class<?> cls = Class.forName("reflect.Person");
        Object obj = cls.newInstance();



    }

    public static String initCap(String str) {
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
}
