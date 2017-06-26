package classload;

/**
 * Created by bryantchang on 2017/6/26.
 * 1、通过子类引用父类的静态字段,不会导致子类初始化
 * 2、通过数组定义来引用类,不会触发该类的初始化
 */
class SuperClass {
    static{
        System.out.println("SuperClass init");
    }
    public static int value = 123;
}

class SubClass extends SuperClass {
    static{
        System.out.println("SubClass init");
    }
}
public class NotInitialization {
    public static void main(String[] args) {
//        System.out.println(SubClass.value);

        System.out.println("------------------");

        SuperClass[] sca = new SuperClass[10];
    }
}
