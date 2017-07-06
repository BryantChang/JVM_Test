package jiekou;

/**
 * Created by bryantchang on 2017/7/6.
 * 接口是抽象方法和全局常量的集合
 * 可以用接口实现多继承
 * 一个类(非抽象类)可以实现多个接口,但必须实现接口中的抽象方法
 *
 *
 * 当一个子类实现了多个接口之后,并且接口通过子类进行实例化,多个父接口之间
 * 允许互相转换的
 *
 * 接口中只能存在public权限,abstract可以省略,抽象类中不能省略
 * 抽象类可以实现接口,接口不能继承抽象类
 * 一个接口能够使用extends继承多个父接口
 * 接口可以定义内部结构使用static定义的内部接口就相当于外部接口
 *
 * 使用接口和对象多态性结合之后,对参数的统一更加明确
 * 接口定义的是一种标准
 */
interface IMessage {
    public static final String MSG = "BryantChang";
    public void print();
}
interface INews {
    public String get();
}

class MessageImpl implements IMessage, INews {
    public void print(){
        System.out.println(IMessage.MSG);
    };
    public String get(){
        return IMessage.MSG;
    };
}

public class TestDemoInterface {
    public static void main(String[] args) {
        IMessage m = new MessageImpl();
        m.print();
    }
}
