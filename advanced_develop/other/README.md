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




