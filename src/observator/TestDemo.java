package observator;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by bryantchang on 2017/7/9.
 *
 * 观察者设计模式
 * 一触即发
 * 需要两个程序结构
 *
 * 1、Observer接口 观察者
 * update
 * 2、Observerable类  被观察者
 */

class Person implements Observer {
    @Override
    public void update(Observable o, Object arg) {//一旦关注的事情发生了变化//arg 新的内容
        if(o instanceof House) {
            if(arg instanceof Double) {
                System.out.println("放假上涨 新价格:" + arg);
            }
        }
    }
}

class House extends Observable {
    private double price;

    public House(double price) {
        this.price = price;
    }

    public void setPrice(double price) {
        if(price > this.price) {
            super.setChanged();
            super.notifyObservers(price);//唤醒所有的观察者
        }
        this.price = price;
    }
}
public class TestDemo {

    public static void main(String[] args) {
        Person per1 = new Person();
        Person per2 = new Person();
        Person per3 = new Person();
        House house = new House(80000.00);
        house.addObserver(per1);
        house.addObserver(per2);
        house.addObserver(per3);
        house.setPrice(100000.00);
    }
}
