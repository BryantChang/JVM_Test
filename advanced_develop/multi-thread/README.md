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


### 实现Runnable接口


* Thread有单继承的局限


#### Runnable 接口定义

```java
@FunctionalInterface//函数式接口，只有一个方法，run()
public interface Runnable {
    public abstract void run();
}
```


* 利用Runnable定义线程主体类


```java
class MyThread implements Runnable {
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
```


* 启动多线程
* 多线程的启动都是使用Thread类的start()方法

```java
public class TestDemo {
    public static void main(String[] args)  {
        MyThread mt1 = new MyThread("thread1");
        MyThread mt2 = new MyThread("thread2");
        MyThread mt3 = new MyThread("thread3");
        new Thread(mt1).start();
        new Thread(mt2).start();
        new Thread(mt3).start();
    }
}


public class TestDemo {
    public static void main(String[] args)  {
//        MyThread mt1 = new MyThread("thread1");
//        MyThread mt2 = new MyThread("thread2");
//        MyThread mt3 = new MyThread("thread3");
        new Thread(()-> System.out.println("Hello World")).start();

    }
}
```


### Thread 和 Runnable的区别

* 使用形式，Runnable使用更好，避免了多继承


```java
public class Thread implements Runnable

//run()方法

@Override
public void run() {
    if (target != null) {
        target.run();
    }
}

//构造方法
public Thread(Runnable target) {
    init(null, target, "Thread-" + nextThreadNum(), 0);
}


//init方法

private void init(ThreadGroup g, Runnable target, String name,
                  long stackSize) {
    init(g, target, name, stackSize, null);
}


private void init(ThreadGroup g, Runnable target, String name,
                  long stackSize, AccessControlContext acc) {
    ...
    this.target = target;
    ...
}
```


### 线程的继承结构

![IMG3](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img3.png)


* 在多线程的处理上使用了代理设计模式，在实际卡法中，使用Runnable有一个特点，使用Runnable实现的多线程的程序类，可以更好地描述数据共享的概念（并不是Thread不能）

### 使用Thread实现数据操作---产生若干个线程进行同一数据处理操作

```java
class MyThread extends Thread {
    private int ticket = 10;

    @Override
    public void run() {
        for (int x = 0; x < 20; x++) {
            if(this.ticket > 0) {
                System.out.println("sale, ticket=" + this.ticket);
            }
        }
    }
}

public class TestDemo {
    public static void main(String[] args)  {
        MyThread mt1 = new MyThread();
        MyThread mt2 = new MyThread();
        MyThread mt3 = new MyThread();
        mt1.start();
        mt2.start();
        mt3.start();
    }
}
```

* 内存关系


```java
public class TestDemo {
    public static void main(String[] args)  {
        MyThread mt = new MyThread();
        new Thread(mt).start();
        new Thread(mt).start();
        new Thread(mt).start();
    }
}
```

![IMG4](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img4.png)

* Thread子类有start()方法，还要调用其他的start()方法，而Runnable子类更自然














