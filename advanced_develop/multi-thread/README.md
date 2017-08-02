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


* 在多线程的处理上使用了代理设计模式，在实际开发中，使用Runnable有一个特点，使用Runnable实现的多线程的程序类，可以更好地描述数据共享的概念（并不是Thread不能）

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

### 死锁

* 同步的本质：一个线程等待另一个线程执行完毕后，自己才能执行，如果多个线程间彼此都在等待着（同步），就会造成死锁

* 死锁举例

```java
class Pen {
    public synchronized void get(Note note) {
        System.out.println("get note");
        note.result();
    }
    public synchronized void result() {
        System.out.println("get note result");
    }
}

class Note {
    public synchronized void get(Pen pen) {
        System.out.println("get pen");
        pen.result();
    }
    public synchronized void result() {
        System.out.println("get pen result");
    }
}
public class DeadLock implements Runnable{
    private static Note note = new Note();
    private static Pen pen = new Pen();

    public DeadLock() {
        new Thread(this).start();
        pen.get(note);
    }
    public static void main(String[] args) {
        new DeadLock();
    }

    @Override
    public void run() {
        note.get(pen);
    }
}

```


* 数据要想完整操作就要使用同步，但同步太多会产生死锁。

### 生产者与消费者模型

#### 基础模型

* 生产者与消费者问题是经典的供求案例provider,consumer
* 生产者负责生产数据，生产者每当生产一个数据之后，消费者会取走
* 生产数据
    - title=titleA note=noteA
    - title=titleB note=noteB

![IMG10](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img10.png)

```java
class Data {
    private String title;
    private String note;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}


class DataProvider implements Runnable {
    private Data data;

    public DataProvider(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (int x = 0; x < 50; x++) {
            if(x % 2 == 0) {
                this.data.setTitle("titleA");
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.data.setNote("noteA");
            }else {
                this.data.setTitle("titleB");
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.data.setNote("noteB");
            }
        }
    }
}

class DataConsumer implements Runnable {
    private Data data;

    public DataConsumer(Data data) {
        this.data = data;
    }


    @Override
    public void run() {
        for (int x = 0; x < 50; x++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.data.getTitle() + "=" + this.data.getNote());
        }
    }
}
public class DeadLock{
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(new DataProvider(data)).start();
        new Thread(new DataConsumer(data)).start();
    }
}

```

* 数据不完整
* 数据重复设置或取出

#### 解决同步问题

* 使用synchronized关键字实现数据同步

#### 解决数据重复操作问题

* 等待与唤醒机制
* Object类中提供的方法
    - wait() -- 死等
    - notify() -- 唤醒第一个等待线程
    - notifyAll() -- 唤醒全部等待线程，哪个优先级高，哪个有可能先执行

```java
public final void wait() throws InterruptedException {
    wait(0);
}
```

```java
public final native void notify();
```

```java
public final native void notifyAll();
```

#### 通过等待与唤醒机制解决数据重复问题


```java
class Data {
    private String title;
    private String note;
    private boolean flag = false; //false允许生产,但不允许取走 true允许取走,不允许生产
    public synchronized void set(String title, String note) {
        if(this.flag == true) { //现在不允许生产
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.title = title;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.note = note;
        this.flag = true;
        super.notify();
    }

    public synchronized void get() {
        if(flag == false) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.title + "=" + this.note);
        this.flag = false;//已经生产过了,不允许重复生产
        super.notify();
    }

}

class DataProvider implements Runnable {
    private Data data;
    public DataProvider(Data data) {
        this.data = data;
    }
    @Override
    public void run() {
        for (int x = 0; x < 50; x++) {
            if(x % 2 == 0) {
                this.data.set("titleA", "noteA");

            }else {
                this.data.set("titleB", "noteB");
            }
        }
    }
}
class DataConsumer implements Runnable {
    private Data data;
    public DataConsumer(Data data) {
        this.data = data;
    }
    @Override
    public void run() {
        for (int x = 0; x < 150; x++) {
            data.get();
        }
    }
}
public class DeadLock{
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(new DataProvider(data)).start();
        new Thread(new DataConsumer(data)).start();
    }
}
```

* wait()与sleep()的区别
    - sleep()是Thread定义的方法，到了一定时间后，休眠的线程可以自动唤醒
    - wait()是Object的方法，必须调用notify和notifyAll的方法才能够唤醒


### 线程池

#### 线程池定义

* 所谓的线程池指的是多个线程封装在一起进行操作
* 针对于某一项任务使用一组线程捆绑在一起执行
* 存在以下的几种情况
    - 任务很大，有多少人就要多少人，一直到完成（无限长度）
    - 只能招聘10个人（限定长度）
    - 只能一个人做（单线程）
* concurrent包的两个核心接口
    - ExecutorService(普通的线程池)
    - ScheduledExecutorService(调度线程池)
    - 创建线程池一般可以使用Executors类完成，有如下几个方法
        + public static ExecutorService newCachedThreadPool()创建无大小限制的线程池
        + public static ExecutorService newFixedThreadPool(int nThreads)创建固定大小的线程池
        + public static ExecutorService newSingleThreadExecutor()单线程池
        + public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)调度定时调度池

#### 线程池实现

* 无限大小的线程池

```java
public class TestDemo {
    public static void main(String[] args) {
        //创建了一个线程池的模型
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int x = 0; x < 10; x++) {
            int index = x;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "x=" + index);
                }
            });

        }
        executor.shutdown();
    }
}
```


* 创建单线程池

```java
public class TestDemo {
    public static void main(String[] args) {
        //创建了一个线程池的模型
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int x = 0; x < 10; x++) {
            int index = x;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "x=" + index);
                }
            });

        }
        executor.shutdown();
    }
}
```


* 创建固定长度线程池

```java
public class TestDemo {
    public static void main(String[] args) {
        //创建了一个线程池的模型
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int x = 0; x < 10; x++) {
            int index = x;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "x=" + index);
                }
            });

        }
        executor.shutdown();
    }
}
```


* 定时调度池

```java
public class TestDemo {
    public static void main(String[] args) {
        //创建了一个线程池的模型

        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(3);
        for (int x = 0; x < 10; x++) {
            final int index = x;
            threadPool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ",x=" + index);
                }
            },3, 2, TimeUnit.SECONDS);//3秒后开始执行，每两秒执行一次
        }
    }
}
```


### ThreadLocal类

* 可以减少一些重要的引用传递


```java
class Message1 {
    private String note;
    public void setNote(String note) {
        this.note = note;
    }
    public String getNote() {
        return note;
    }
}

class MessageConsumer1 {
    public void print(Message1 msg) { //明确接收Message类对象
        System.out.println(Thread.currentThread().getName() + "=" + msg.getNote());
    }
}
public class Test {
    public static void main(String[] args) {
        new Thread(() -> {
            Message1 msg = new Message1();
            msg.setNote("aaa");
            new MessageConsumer1().print(msg);
        }, "A").start();

        new Thread(() -> {
            Message1 msg = new Message1();
            msg.setNote("bbb");
            new MessageConsumer1().print(msg);
        }, "B").start();
    }
}

```

* 以上代码中明确使用了引用传递将Message1对象传递给MessageConsumer1类的print()方法
* 如何不传递类对象来实现
* 使用其他类实现参数传递

```java
class Message1 {
    private String note;
    public void setNote(String note) {
        this.note = note;
    }
    public String getNote() {
        return note;
    }
}

class MessageConsumer1 {
    public void print() { //明确接收Message类对象
        System.out.println(Thread.currentThread().getName() + "=" + MyUtil1.message.getNote());
    }
}

class MyUtil1 {
    public static Message1 message;
}
public class Test {
    public static void main(String[] args) {
        new Thread(() -> {
            Message1 msg = new Message1();
            msg.setNote("aaa");
            MyUtil1.message = msg;
            new MessageConsumer1().print();
        }, "A").start();

        new Thread(() -> {
            Message1 msg = new Message1();
            msg.setNote("bbb");
            MyUtil1.message = msg;
            new MessageConsumer1().print();
        }, "B").start();
    }
}
```

```
执行结果：
B=bbb
A=bbb
```

![IMG11](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/multi-thread/imgs/img11.png)

* 如果想要明确的标记出每一个线程的具体的对象信息，则需要使用ThreadLocal，其多保存了一个CurrentThread
* 重要的对象---资源链接信息


```java
/**
 * Created by bryantchang on 2017/7/9.
 * ThreadLocal类
 * 可以减少引用传递
 * 明确的标记每一个线程的具体的对象信息,就需要使用ThreadLocal,多保存了一个currentThread
 * 重要方法:
 * get取得一个数据
 * set保存一个数据
 * remove删除数据
 */
class Message {
    private String note;
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}

class MyUtil{
    public static ThreadLocal<Message> local = new ThreadLocal<>();
    public static void set(Message msg) {
        local.set(msg);
    }
    public static Message get() {
        return local.get();
    }
}

class MessageConsumer {
    public void print() {//consumer必须明确接受Message类对象
        System.out.println(Thread.currentThread().getName() + MyUtil.get().getNote());
    }
}
public class TestDemo {
    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.setNote("hello worlda");
                MyUtil.set(msg);
                new MessageConsumer().print();
            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.setNote("hello worldb");
                MyUtil.set(msg);
                new MessageConsumer().print();
            }
        }, "B").start();
    }
}
```








