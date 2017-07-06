package innerclass;

/**
 * Created by bryantchang on 2017/7/6.
 *
 * 通过外部方法访问内部类方法
 * 非static
 * 外部类.内部类 内部类对象=new 外部类对象().new 内部类对象();
 *
 *
 * static
 * 外部类.内部类 内部类对象=new 外部类对象.内部类对对象();
 */

class Outer{
    static private String msg = "hello world!";
    static class Inner{
        public void print(){
            System.out.println(msg);
        }
    }

}


public class Main {
    public static void main(String[] args) {
        Outer.Inner in = new Outer.Inner();
        in.print();
    }
}
