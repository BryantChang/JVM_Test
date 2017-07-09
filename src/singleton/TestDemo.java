package singleton;

/**
 * Created by bryantchang on 2017/7/8.
 * 单例模式
 * 实例是单例类的实例是私有的
 * 懒汉式 && 饿汉式
 * 饿汉式:只要加载就会实例化实例对象,希望整体的操作中只有一个实例化对象(private)
 * 懒汉式:
 * 多例只是比单利产生了多个内部产生实例化对象的类
 */

//饿汉式
class SingletonHungry {
    private static final SingletonHungry INSTANCE = new SingletonHungry();
    public static SingletonHungry getInstance() {
        return INSTANCE;
    }
    public void print() {
        System.out.println("Hello World");
    }
}


//懒汉式
class SingletonLazy {
    private static SingletonLazy instance;
    public static SingletonLazy getInstance() {
        if(instance == null) {
            instance = new SingletonLazy();
        }
        return instance;
    }
}


public class TestDemo {
    public static void main(String[] args) {

    }
}
