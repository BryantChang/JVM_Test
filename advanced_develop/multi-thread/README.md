# 多线程编程

## 进程与线程的区别

* 在操作系统中，已被程序的执行周期，称为一个进程
* 线程是进程的基础上更小的划分，进程要比线程慢


### 举例：服务器应用


![IMG1](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img1.png)

* 高并发指的是访问的线程数很多，常见问题，服务器内存不够用


## 多线程实现（继承Thread类）

* 实现多线程的主类
    - 继承Thread类
    - 实现Runnable或Callable接口


### 继承Thread类

* 继承Thread类，覆写run()方法
* 多线程启动：public void start();
* 每一个线程对象只能启动1次

```java
class MyThread extends Thread {
    private String title;

    public MyThread(String title) {
        this.title = title;
    }


    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(this.title + ",x=" + i);
        }
    }
}


public class TestDemo {
    public static void main(String[] args)  {
        MyThread mt1 = new MyThread("thread1");
        MyThread mt2 = new MyThread("thread2");
        MyThread mt3 = new MyThread("thread3");
        mt1.start();
        mt2.start();
        mt3.start();

    }
}
```


### Thread的start()方法源代码

```java
public synchronized void start() {
        if (threadStatus != 0)
            throw new IllegalThreadStateException();//RuntimeException的子类
        group.add(this);
        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
            }
        }
    }

private native void start0();
```


* start0() 方法是一个只声明而未实现的方法，native 指的是调用的本机的原生系统函数

![IMG2](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img2.png)
















