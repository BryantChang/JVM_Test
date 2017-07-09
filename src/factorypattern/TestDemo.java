package factorypattern;

/**
 * Created by bryantchang on 2017/7/8.
 * 工厂模式
 */
interface IFriut {
    public void eat();
}

class Factory{
    public static IFriut getInstance(String className) {
        if("apple".equals(className)) {
            return new Apple();
        }
        if("orange".equals(className)) {
            return new Orange();
        }
        return null;
    }
}

class Apple implements IFriut {
    @Override
    public void eat() {
        System.out.println("吃苹果");
    }
}

class Orange implements IFriut {
    @Override
    public void eat() {
        System.out.println("吃橘子");
    }
}
public class TestDemo {
    public static void main(String[] args) {

    }
}
