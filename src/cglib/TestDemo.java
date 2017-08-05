package cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by bryantchang on 2017/8/5.
 */

class Message {
    public void send() {
        System.out.println("hello world");
    }
}

class MyProxy implements MethodInterceptor {
    private Object target;

    public MyProxy(Object target) {
        this.target = target;
    }


    public void prepare() {
        System.out.println("prepare");
    }


    public void clear() {
        System.out.println("clear");
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        this.prepare();
        Object ret = method.invoke(this.target, objects);
        this.clear();
        return ret;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Message msg = new Message();
        Enhancer enhancer = new Enhancer();  //代理处理类,负责代理关系操作
        enhancer.setSuperclass(msg.getClass());//把本类作为标识
        enhancer.setCallback(new MyProxy(msg));//动态配置了类之间的代理关系

        Message tmp = (Message)enhancer.create();
        tmp.send();
    }
}
