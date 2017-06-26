package classload;

/**
 * Created by bryantchang on 2017/6/26.
 * 常量在编译时会被存储在常量池中,本质上没有直接引用到定义常量的类,
 * 因此不会触发常量的类的初始化
 */

class ConstClass{
    static{
        System.out.println("ConstClass init");
    }
    public static final String HELLOWORLD = "hello world";
}
public class NotInitialization2 {
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLOWORLD);
    }
}
