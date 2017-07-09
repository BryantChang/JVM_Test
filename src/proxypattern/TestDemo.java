package proxypattern;

/**
 * Created by bryantchang on 2017/7/8.
 * 代理模式:两个子类同时实现一个借口,一个负责真实实现,一个用来辅助实现业务
 */

interface ISubject{
    public void save();
}

class RealSubject implements ISubject{
    @Override
    public void save() {
        System.out.println("救人");
    }
}

class ProxySubject implements ISubject {
    private ISubject subject;//真正的操作业务
    public ProxySubject(ISubject subject) {//创建代理对象时要创建真实主题
        this.subject = subject;
    }
    public void broke() {
        System.out.println("破门而入");
    }
    public void get() {
        System.out.println("获得表彰");
    }
    @Override
    public void save() {
        this.broke();
        this.subject.save();
        this.get();
    }
}

class Factory{
    public static ISubject getInstance() {
        return new ProxySubject(new RealSubject());
    }
}

public class TestDemo {
    public static void main(String[] args) {
        ISubject sub = Factory.getInstance();
        sub.save();

    }
}
