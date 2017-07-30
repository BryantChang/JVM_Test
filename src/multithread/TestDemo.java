package multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by bryantchang on 2017/7/8.
 * 多线程的主类有两类途径
 * 1、继承Thread类
 * 2、实现Runnable,Callable接口(单继承)
 * native调用本机的原生系统函数
 * Thread与Runnable的区别
 * Thread类是Runnable接口的子类
 * 在多线程的处理上使用的是代理设计模式Thread类是代理 自己实现的类是真实
 * 使用Runnable比Thread更好的体现数据共享操作
 * 线程状态:
 * 当多线程调用start方法,进入到就绪状态等待调度
 *
 * 通过Callable实现多线程
 * call方法
 * Runnable中的run方法没有返回值
 * FutureTask 实现了FutureRunnable
 * FutureRunnable继承了Runnable和Future
 *
 *
 * 多线程操作
 * 取得当前线程对象:Thread.currentThread
 * 主方法本身就是一个线程,所有线程都由主线程负责创建
 * 线程休眠sleep(ms)真正进入方法的对象可能是一个或多个
 *
 *
 * 同步问题
 * 不同步,同一时刻可能有多个线程进入代码,但好处是处理速度快,数据访问是不安全的操作
 * 同步指的是不是所有线程一起进入,而是一个个进入
 * 如果要实现锁的功能,可以采用synchronized关键字来进行处理,两种模式,1)同步代码块,2)同步方法
 * 若使用同步代码块,一定要设置一个锁定对象(this)实在方法里拦截的,进入到方法中的线程依然会有多个
 *
 * 同步代码块 synchronized修饰代码
 *
 * 死锁:线程之间互相等待,整个程序暂时中断执行
 */

//集成Thread类,继承Thread类,复写run方法


class MyThread implements Runnable {
    private int ticket = 10;
    @Override
    public void run() {
        for (int x = 0; x < 20; x++) {
            synchronized (this) {
                if(this.ticket > 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "卖票, ticket=" + this.ticket--);
                }
            }
        }
    }
}


public class TestDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread mt = new MyThread();
        new Thread(mt, "A").start();
        new Thread(mt, "B").start();
        new Thread(mt, "C").start();
    }
}
