package reflection;

/**
 * Created by bryantchang on 2017/7/9.
 *
 * 反射,根据对象取得对象的来源信息"反"的信息的核心是Object类取得Class类的对象
 * Class 类的三种实例化对象
 * 1、任何的实例化对象可以通过Object类中的getClass()取得Class类对象
 * 2、"类.Class"直接根据某一个具体的类来取得实例化对象
 * 3、使用Class类中提供的方法forName获取类的对象
 *
 * 除了第一种会产生实例化对象,其他两种都不会产生,取得Class类对象可以直接通过反射实例化对象,newInstance
 *
 * 使用反射实现工厂设计模式
 * 传统工厂类的最大弊端:关键字new
 * 使用泛型
 */
interface IFruit {
    public void eat();
}

interface IMessage{
    public void print();
}

class IMessageImpl implements IMessage {
    @Override
    public void print() {
        System.out.println("Hello world");
    }
}
class Apple implements IFruit{
    @Override
    public void eat() {
        System.out.println("eat apple");
    }
}


class Factory {
    private Factory() {}
    public static<T> T getInstance(String className) {
        T obj = null;
        try {
            obj = (T)Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}

public class TestDemo {
    public static void main(String[] args) {
//        System.out.println(Apple.class.getName());
        IFruit fruit = Factory.getInstance("reflection.Apple");
        fruit.eat();
    }
}
