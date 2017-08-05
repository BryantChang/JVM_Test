package proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Created by bryantchang on 2017/8/5.
 */
interface ISubject {
    public void eat(String msg, int num);
}

class RealSubject implements ISubject {
    @Override
    public void eat(String msg, int num) {
        System.out.println("我要吃" + num + "分量的" + msg);
    }
}

class ProxySubject implements InvocationHandler{
    private Object target; //绑定任意接口对象,使用Object描述

    /**
     * 实现真是的绑定处理,同时返回代理对象
      * @param target
     * @return 返回一个代理对象(这个对象是根据接口定义动态创建形成的代理对象)
     */
    public Object bind(Object target) {
        this.target = target;//保存真实主题对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public void prepare() {
        System.out.println("prepare");
    }
    public void clear() {
        System.out.println("clear");
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        this.prepare();
        Object ret = method.invoke(this.target, args);
        this.clear();
        return ret;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        ISubject subject = (ISubject)new ProxySubject().bind(new RealSubject());
        subject.eat("aaa", 20);
    }
}
