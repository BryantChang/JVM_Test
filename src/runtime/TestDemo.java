package runtime;

/**
 * Created by bryantchang on 2017/7/9.
 *
 * Runtime是一个运行时的描述类
 * 单例设计模式,一个JVM中只有一个Runtime类对象
 * 饿汉式
 *
 * 方法:
 * freeMemory:取得空余内存大小
 * totalMemory:可用总空间
 * maxMemory:最大的可用空间
 *
 *
 * gc()执行垃圾收集处理
 * gc有两种处理形式,自动处理&&Runtime.gc()
 *
 *
 * System类
 * Syetem.gc() === Runtime.getRuntime().gc()
 *
 *
 * 析构函数:复写Object类中的finalize()方法
 * 面试题:解释final finally finalize的区别
 * final是关键字用于定义不能够被继承的父类,不能被复写的变量或常量
 * finally异常处理
 * finalizeObject累的方法,被释放前所做的事情
 */
class Person{
    public Person() {
        System.out.println("born");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("going to die");
        throw new Exception("exception");
    }
}
public class TestDemo {

    public static void main(String[] args) {
        Person per = new Person();
        per = null;
        System.gc();
        System.out.println("212");
    }

    public static double byte2Mb(long bytenum) {
        return (double)bytenum/1024/1024;
    }
}
