## 其他知识点

### Runtime类

* 每一个JVM进程中都会存在一个Runtime类的对象
* 构造方法私有化，因为其只能有一个实例化对象，该类使用的是单例设计模式(饿汉式)，在该类中有取得Runtime实例化对象的方法getRuntime()
* 可以通过Runtime观察当前的内存使用情况
    - freeMemory()取得所有空余内存空间
    - totalMemory()取得总空间大小
    - maxMemory()取得最大的可用空间
    - gc()执行垃圾回收

### System类

* 取得当前日期时间数 public static long currentTimeMillis()
    - 此方法可以获取某个方法执行的时间
* gc()调用了Runtime类中的gc操作

```java
public static void gc() {
    Runtime.getRuntime().gc();
}
```

* 析构函数:复写Object类中的finalize()方法

```java
protected void finalize() throws Throwable
```

```
面试题:解释final finally finalize的区别
final是关键字用于定义不能够被继承的父类,不能被复写的变量或常量
finally异常处理
finalize()Object类的方法,被释放前所做的事情
```

### 对象克隆

* 克隆指的是对象的复制
* Object类支持对象克隆方法clone()

```java
protected Object clone() throws CloneNotSupportedException
```

* 不是所有类的对象都支持克隆，需要实现Cloneable接口，该接口没有任何抽象方法，只是一个标识接口，表示一种能力

### 观察者设计模式

* 观察者模式的两个程序结构
    -   观察者：public interface Observer
        +   void update(Observable o, Object arg)
    -   被观察者：public class Observable extends Object

```java
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

```

### 字节流与字符流

* File类不支持文件内容处理
* 流：
    - 输入流
    - 输出流
* 字节流--原生操作:
    - InputStream OutputStream
* 字符流（经过处理的字节流，适用于处理中文）
    - Reader Writer
* 基本流程
    - 根据文件路径，创建File类对象
    - 根据字节流或字符流的子类实例化父类对象
    - 进行数据的读取或写入操作
    - 关闭流(close())

### OutputStream--字节输出流

* 类定义

```java
public abstract class OutputStream extends Object implements Closeable, Flushable{}
```

* OutputStream 实现了Closeable, Flushable两个接口，有两个方法
    - public void close() throws IOException
    - public void flush() throws IOException
* OutputStream类中的其他方法
    - public void write(byte[] b) throws IOException  -- 将给定的字节数组内容全部输出
    - public void write(byte[] b, int off, int len) throws IOException -- 将部分数组内容输出
    - public abstract void write(int b) throws IOException -- 输出单个字节
* 子类的构造方法，若要进行文件操作，可以使用FileOutputStream类来处理
    - public FileOutputStream(File file) throws FileNotFoundException
    - public FileOutputStream(File file, boolean append) throws FileNotFoundException

![IMG1](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img1.png)

### FileOutputStream 使用案例

```java
public class TestDemo {
    public static void main(String[] args) throws Exception {
        //输出字节流
        File file = new File("/Users/bryantchang/Desktop/study/codes/for_interview/JVM_test/files/hello.txt");
        if(!file.getParentFile().exists()) {//保证父目录存在
            file.getParentFile().mkdirs();//创建目录
        }
        OutputStream output = new FileOutputStream(file, true);
        String msg = "hello world";
        output.write(msg.getBytes());
        output.close();
    }
}
```

* OutputStream中最重要的方法为：
    - public void write(byte[] b, int off, int len) throws IOException

## AutoCloseble

* 自动的关闭处理

## InputStream（字节输入流）

![IMG2](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img2.png)

```java
public abstract class InputStream extends Object implements Closeable{}
```

* 方法：
    - public int read(byte[] b) throws IOException  -- 读取数据到字节数组之中，返回数据的读取个数，如果此时开辟的字节数组大小大于数据大小，则返回读取个数，如果读取的数据大于数组的内容，则返回数组长度，如果没有数据，返回-1；
    - public int read(byte[] b, int off, int len) throws IOException -- 读取部分数据到字节数组之中，返回数据的读取个数，每次读取数组的部分内容，如果满了，则返回len，如果没满，则返回数组长度，没有数据返回-1；
    - public abstract int read() throws IOException -- 读取单个字节

* InputStream举例

```java
public class TestDemo {
    public static void main(String[] args) throws Exception {
        //输出字节流
        File file = new File("/Users/bryantchang/Desktop/study/codes/for_interview/JVM_test/files/hello.txt");
        InputStream input = new FileInputStream(file);
        if(file.exists()) {
            byte data[] = new byte[1024];
            int len = input.read(data);
            System.out.println(new String(data, 0, len));
        }
    }
}
```

## 字符输入流Writer

* 字符适合处理中文数据

```java
public abstract class Writer extends Object implements Appendable, Closeable, Flushable{}
```

* Writer类中也提供write()方法，而且都是接收char型的，但有一个方法可以直接接收String
    - public void write(String str) throws IOException

![IMG3](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img3.png)


### Writer 举例

```java
public class TestDemo {
    public static void main(String[] args) throws Exception {
        //输出字节流
        File file = new File("/Users/bryantchang/Desktop/study/codes/for_interview/JVM_test/files/hello.txt");
        if(!file.getParentFile().exists()) {//保证父目录存在
            file.getParentFile().mkdirs();//创建目录
        }
        String msg = "世界和平";
        Writer out = new FileWriter(file);
        out.write(msg);
        out.close();
    }
}

```

## Reader -- 字符输入流

* 抽象类，FileReader

![IMG4](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img4.png)

```java
public abstract class Reader extends Object implements Readable, Closeable{}
```

* 字符流在读取或写入时更适合处理中文

## 字节流与字符流的关系

![IMG5](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img5.png)

## 转换流

* 字符流与字节流也可以进行相互的转换处理
    - OutputStreamWriter--将字节输出流变为字符输出流
    - InputStreamReader--将字节输入流变为字符输入流
* 两个类的继承关系

### OutputStreamWriter

```java
public class OutputStreamWriter extends Writer{
    public OutputStreamWriter(OutputStream out, Charset cs){}
}
```

### InputStreamReader

```java
public class InputStreamReader extends Reader {
    InputStreamReader(InputStream in){}
}
```


![IMG6](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img6.png)

* 分析FileOutputStream, FileInputStream, FileWriter和FileReader的继承结构

![IMG7](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img7.png)


## 反射机制

* "正"的操作，先导入一个包才能产生实例化操作
* "反"指的是通过对象找到出处，核心处理时Object中取得Class类对象的方法
    - public final Class<?> getClass()
    - 类，构造，普通，成员等

```java
public class TestDemo {
    public static void main(String[] args) throws Exception {
        Date date = new Date();//正常来讲,必须通过类才能产生实例化对象
        System.out.println(date.getClass());//class java.util.Date
    }
}
```


## Class类的三种实例化方法

* Class类是描述整个class的类
* Class类对象的产生模式
* 实例化对象可以通过getClass()方法取得Class类对象泛型必须写问号

```java
public class TestDemo {
    public static void main(String[] args) throws Exception {
        Class<?> cls = new Date().getClass();
        System.out.println(cls.getName());
    }
}
```

* 类.class,直接根据某个具体的类来取得Class类的实例化对象

```java
public class TestDemo {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Date.class;
        System.out.println(cls.getName());
    }
}
```


* 使用Class类提供的方法forName()

```java
public class TestDemo {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("java.util.Date");
        System.out.println(cls.getName());
    }
}
```

* 取得Class类对象可以利用反射进行实例化(newInstance)

```java
public T newInstance() throws InstantiationException,IllegalAccessException
```

```java
public class TestDemo {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("java.util.Date");
        Object obj = cls.newInstance();
        System.out.println(obj);
    }
}
```

## 反射与工厂方法

* 传统工厂类设计

```java
interface IFruit{
    public void eat();
}

class Factory {
    public static IFruit getInstance(String className) {
        if("apple".equals(className)) {
            return new Apple();
        }
        if("orange".equals(className)) {
            return new Orange();
        }
        return null;
    }
}

class Apple implements IFruit {
    public void eat() {
        System.out.println("eat apple");
    }
}

class Orange implements IFruit {
    public void eat() {
        System.out.println("eat orange");
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IFruit fruit = Factory.getInstance("apple");
        fruit.eat();
    }
}
```

* 传统工厂类的最大弊端---new

![IMG8](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img8.png)

* 解决方法：使用反射来实现工厂类，使用newInstance和forName

```java
interface IFruit{
    public void eat();
}

class Factory {
    public static IFruit getInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        IFruit fruit = null;
        try {
            fruit = (IFruit)Class.forName(className).newInstance();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return fruit;
    }
}

class Apple implements IFruit {
    public void eat() {
        System.out.println("eat apple");
    }
}

class Orange implements IFruit {
    public void eat() {
        System.out.println("eat orange");
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IFruit fruit = null;
        try {
            fruit = Factory.getInstance(Orange.class.getName());
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
```

* 缺陷：如果现在又10w个接口，那么就需要有个10w个接口，因此使用泛型来解决
* 解决：

```java
interface IFruit{
    public void eat();
}

interface IMessage{
    public void print();
}


class MessageImpl implements IMessage {
    @Override
    public void print() {
        System.out.println("hello");
    }
}

class Factory {
    public static<T> T getInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        T obj = null;
        try {
            obj = (T)Class.forName(className).newInstance();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}

class Apple implements IFruit {
    public void eat() {
        System.out.println("eat apple");
    }
}


public class TestDemo {
    public static void main(String[] args) {
        try {
            IFruit fruit = Factory.getInstance(Apple.class.getName());
            fruit.eat();
            IMessage message = Factory.getInstance(MessageImpl.class.getName());
            message.print();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
```


## Collection集合接口（为了查找）

* 在Java的类集中，有两个核心的接口
    - Collection:每一次进行数据操作时只能对单个对象进行处理，单个集合保存的最大父接口。
    - Map

* Collection接口定义如下

```java
public interface Collection<E> extends Iterable<E>{
    //JDK1.5开始，避免了ClassCastException
    Iterator<T> iterator(); //1.5之前是直接由Collection提供的
}

```

* 该类中的常用方法如下：
    - public boolean add(E e)--向集合中添加数据
    - public boolean addAll(Collection<? extends E> c)--向集合中添加一组数据
    - public void clear()---清空集合
    - public boolean contains(Object o)--查询集合中是否包含某个元素，需要equals()方法支持
    - public boolean remove(Object o)--删除数据，需要equals()支持
    - public int size()--获取集合的长度
    - <T> T[] toArray(T[] a)-- 将集合变为对象数组返回
    - public Iterator<T> iterator()-- 取得Iterator的接口对象，用于输出

* 在实际开发中很少使用Collection接口，使用子接口，List(允许重复),Set(不允许重复)

![IMG9](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img9.png)


## List接口定义

```java
public interface List<E> extends Collection<E>{}
```

* 核心方法
    - public E get(int index)--根据索引返回数据
    - public E set(int index, E element)--根据索引修改数据

* List与Collection相比最重要的方法是多了get方法
* List本身还是接口，如果想取得对象，就要有子类，List的常用子类有三个，ArrayList,Vector,LinkedList

![IMG10](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img10.png)

## ArrayList

* ArrayList是一个针对List接口的数组操作实现

## Vector

* ArrayList和Vector区别
    - 历史时间：ArrayList--JDK1.2  Vector--JDK1.0
    - 处理方式：ArrayList 异步处理，效率较高，Vector 同步处理，但效率较低
    - 数据安全：ArrayList 线程不安全 Vector 线程安全
    - ArrayList支持Iterator ListIterator foreach,Vector支持支持Iterator ListIterator foreach，Enumeration


## LinkedList
* ArrayList和LinkedList区别
* ArrayList存放的是数组，如果不够了会进行动态扩充
* 如果使用ArrayList最好设置初始化大小
* LinkedList是一个完全的链表实现，双向链表，复杂度不同

```java
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}

public ArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
        this.elementData = new Object[initialCapacity];
    } else if (initialCapacity == 0) {
        this.elementData = EMPTY_ELEMENTDATA;
    } else {
        throw new IllegalArgumentException("Illegal Capacity: "+
                                           initialCapacity);
    }
}
```


## Set接口

* Set没有对Collection的方法进行扩充，而List实现了扩充。
* Set方法中没有Get
* HashSet（无序）和TreeSet（升序排列）

![IMG11](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img11.png)

## Map集合（为了查找）

* 保存偶对象（key-value）

```java
public interface Map<K,V>{}
```

* 常用方法
    - public V put(K key,V value)向集合中加入数据
    - public V get(Object key)根据对应的Key取得Value无Key返回Value
    - public Set<K> keySet()取得所有的key信息,不能重复
    - public Collection<V> values()取得value信息，可以重复
    - public Set<Map.Entry<K,V>> entrySet()将Map集合编程Set集合

* 子类
    - HashMap(线程不安全，无序)
    - HashTable
    - TreeMap
    - ConcurrentHashMap

## HashMap

* HashMap原理（线程不安全）
    - 在数据量小的时候是按照链表存储的
    - 当数据量变大之后，为了进行快速的查找，使用红黑树保存，用hash码定位进行保存

## HashTable

* 同步实现，线程安全，性能较差

## ConcurrentHashMap

* HashTable的线程安全性+HashMap的高性能

```java
public class ConcurrentHashMap<K,V> extends AbstractMap<K,V> implements ConcurrentMap<K,V>, Serializable{}
```

![IMG13](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img13.png)

* ConcurrentHashMap工作原理
    - 数据分桶--将大量数据平均分在不同的数据区域中，在进行数据查找时可以避免全部的数据扫描
    - 每一个数据中要有一个分桶的标记(Hashcode)16个
    - 16把写锁，每次写只锁当前数据块，其它不上锁
    
![IMG14](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/other/imgs/img14.png)








