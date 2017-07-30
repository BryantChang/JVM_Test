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

### 线程状态

* 线程启动使用的是start方法，但不意味着调用了start方法就立刻开始执行
* 线程执行需要分配资源，资源不能独占，执行一段时间后需要让出资源，重新等待资源分配

![IMG5](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img5.png)


### Callable实现多线程（JDK1.5 java.util.concurrent）


```java
public interface Callable<V>
V call() throws Exception;
```

* Runnable 的run方法没有返回值 Callable的call方法有返回值

#### 使用Callable定义线程主体类


```java
class MyThread implements Callable<String> {
    private int ticket = 10;

    @Override
    public String call() throws Exception {
        for (int x = 0; x < 20; x++) {
            if(this.ticket > 0) {
                System.out.println("sale, ticket=" + this.ticket);
            }
        }
        return "票卖完了,下次吧!!";
    }
}
```


* 多线程的启动只能通过Thread类的start方法来启动

![IMG6](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img6.png)


### 多线程的常用操作方法

* 多线程的运行状态时不确定的
* Thread类有关名称的方法
    - 构造方法
    - 设置名称
    - 获取名称

```java
public Thread(Runnable target, String name) {
        init(null, target, name, 0);
    }
```

```java
public final synchronized void setName(String name) {
    checkAccess();
    if (name == null) {
        throw new NullPointerException("name cannot be null");
    }

    this.name = name;
    if (threadStatus != 0) {
        setNativeName(name);
    }
}
```

```java
public final String getName() {
    return name;
}
```


* 线程执行过程

![IMG7](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img7.png)


* 在Thread类中提供有取得当前线程对象的方法

```java
public static Thread currentThread()
```

```java
class MyThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}


public class TestDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread mt = new MyThread();
        mt.run();//main
        new Thread(mt).start();
    }
}

```

* 主方法本身就是一个线程，所有线程都是由主线程启动的
* 每当使用java命令来解释程序的时候，表示启动一个新的JVM进程，主方法是其中的一个线程


#### 线程休眠

* 所谓的线程休眠指的是让线程暂缓执行，到指定时间后恢复运行

```java
public static void sleep(long millis) throws InterruptedException
```

* 所有的线程使依次进入到run()方法中的。
* 真正进入到方法的对象可能是多个，也可能是一个，进入代码的顺序可能有差异，但总体上线程之间的执行是并发执行


#### 线程优先级

* 所谓的线程优先级指的是优先级越高，越有可能先执行，在Thread类中提供有如下的优先级设置方法
    - 设置优先级：public final void setPriority(int newPriority)
    - 取得优先级：public final int getPriority()
    - 最高优先级：public final static int MAX_PRIORITY = 10;
    - 中等优先级：public final static int NORM_PRIORITY = 5;
    - 最低优先级：public final static int MIN_PRIORITY = 1;
* 主方法只是一个中等优先级

### 同步与死锁

* 概念要理解--核心问题：每一个县城对象操作的延迟问题或轮番抢占资源的问题

#### 举例 -- 希望让其可以实现多个线程卖票的处理

* 初期实现

```java
class MyThread implements Runnable {
    private int ticket = 10;
    @Override
    public void run() {
        for (int x = 0; x < 20; x++) {
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


public class TestDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread mt = new MyThread();
        new Thread(mt, "A").start();
        new Thread(mt, "B").start();
        new Thread(mt, "C").start();
    }
}
```

* 票数出现了0和负数
* 不同步问题
    - 不同步的好处是处理速度快

![IMG8](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img8.png)

#### 同步的处理

* 所谓的同步指的是所有的线程不是一起进入到方法中执行，而是按照顺序一个一个进来

![IMG9](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img9.png)

* 如果想要实现锁的功能，可以使用synchronized关键字来进行处理，有两种模式
    - 同步代码块
    - 同步方法

* 同步代码块，一般可以锁定当前对象

```java 
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
```

* 这种实现方式实在方法里拦截的，也就是说进入到方法中的线程还会有很多个


* 同步方法

```java
class MyThread implements Runnable {
    private int ticket = 10;
    @Override
    public void run() {
        for (int x = 0; x < 20; x++) {
            this.sale();
        }
    }

    public synchronized void sale() {
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
public class TestDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread mt = new MyThread();
        new Thread(mt, "A").start();
        new Thread(mt, "B").start();
        new Thread(mt, "C").start();
    }
}
```

* 同步虽然可以保证线程安全操作，但执行速度会很慢















