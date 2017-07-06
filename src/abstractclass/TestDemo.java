package abstractclass;

/**
 * Created by bryantchang on 2017/7/6.
 */
/**
 * 内部抽象类中允许使用static,外部抽象类不可以
 * 抽象类不允许被实例化
 * 抽象类中可以不定义抽象方法
 */

abstract class A {
    public abstract void printA();
    static abstract class B {
        public abstract void printB();
    }
}

class X extends A.B {
    public void printB() {
        System.out.printf("1111");
    }
}

public class TestDemo {
}
