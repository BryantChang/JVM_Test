# 泛型

* 可以实现参数转换问题

## 问题的引出

* 提供一个描述坐标的类，需要满足下面的几种格式
    - x=10,y=20
    - x=10.1,y=20.2
    - x=东经20度,y=北纬20度
* Point类中的x和y的属性类型问题
* 使用Object类进行处理
    - 可能会出现ClassCastException（两个没有关系的对象进行转换所带来的异常）
    - 向下转型是不安全的

```java
class Point {
    private Object x;
    private Object y;

    public Object getX() {
        return x;
    }

    public void setX(Object x) {
        this.x = x;
    }

    public Object getY() {
        return y;
    }

    public void setY(Object y) {
        this.y = y;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        //设置整型
        Point p1 = new Point();
        p1.setX(10);
        p1.setY(20);
        int x1 = (Integer) p1.getX();
        int y1 = (Integer) p1.getY();
        System.out.println("x=" + x1 + ",y=" + y1);

        //设置字符串
        Point p2 = new Point();
        p2.setX("东经10度");
        p2.setY("北纬20度");
        String x2 = (String) p2.getX();
        String y2 = (String) p2.getY();
        System.out.println("x=" + x2 + ",y=" + y2);
    }
}
```

## 泛型的实现

* 类定义的时候不会设置类中的属性和方法中参数的具体类型，而是在类使用的时候进行定义
* 泛型---类型标记的声明
* 当开发中能够避免向下转型，则这种安全隐患就被消除了

### 举例

```java
class Point<T> { //T表示参数,是一个占位描述标记
    private T x;
    private T y;

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }
}


public class TestDemo {
    public static void main(String[] args) {
        //设置整型
        Point<String> p1 = new Point();
        p1.setX("东经10度");
        p1.setY("北纬20度");
        String x1 = p1.getX();//避免向下转型
        String y1 = p1.getY();
        System.out.println("x=" + x1 + ",y=" + y1);
    }
}
```


### 通配符

* 泛型可以避免ClassCastException, 但会出现参数统一问题

#### 举例

```java
class Message<T> {
    private T note;

    public T getNote() {
        return note;
    }

    public void setNote(T note) {
        this.note = note;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Message<String> msg = new Message<String>();
        msg.setNote("hello world");
        fun(msg);
    }

    public static void fun(Message<String> tmp) {
        System.out.println(tmp.getNote());
    }
}
```


![IMG1](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/generic-paradigm/imgs/img1.png)

* 通配符：可以接收所有的泛型类型，但又不能允许随意修改

```java
class Message<T> {
    private T note;

    public T getNote() {
        return note;
    }

    public void setNote(T note) {
        this.note = note;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Message<String> msg = new Message<String>();
        msg.setNote("hello world");
        fun(msg);
    }

    //使用通配符"?"描述的是可以接受任意类型,但由于不确定类型,所以无法修改
    public static void fun(Message<?> tmp) {
        System.out.println(tmp.getNote());
    }
}
```

* 在?的基础上出现了两个子通配符
    - ?extends类--设置泛型上限
        + ? extends Number,表示只能设置Number或其子类，如Integer，Double等(定义类或方法)
    - ?super类--设置泛型下限
        + ? extends String,表示只能设置String或Object(定义方法)

### 泛型接口

* 接口实现子类继续使用泛型

```java
package tongpeifu;

/**
 * Created by bryantchang on 2017/7/29.
 */
interface IMessage<T> {
    public void print(T t);
}

//子类定义继续使用泛型
class MessageImpl<T> implements IMessage<T> {
    @Override
    public void print(T t) {
        System.out.println(t);
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IMessage<String> msg = new MessageImpl<String>();
        msg.print("Hello World");
    }
}

```

* 接口实现子类明确给出类型

```java
package tongpeifu;

/**
 * Created by bryantchang on 2017/7/29.
 */
interface IMessage<T> {
    public void print(T t);
}

//子类定义继续使用泛型
class MessageImpl implements IMessage<String> {
    @Override
    public void print(String t) {
        System.out.println(t);
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IMessage<String> msg = new MessageImpl<String>();
        msg.print("Hello World");
    }
}

```


### 泛型方法

* 泛型方法可以单独定义

```java
public class TestDemo {
    public static void main(String[] args) {
        Integer data[] = fun(1,2,3,4);
        for (int tmp: data) {
            System.out.println(tmp);
        }
    }
    //泛型标记的声明
    public static <T> T[] fun(T ... args) {
        return args;
    }
}
```


### Annotation(注解)


* 1、在进行项目开发过程中，会将所有使用到的第三方信息或是程序有关的操作都写进程序里
    - 如果服务器的地址更换了，就意味着需要修改源代码

![IMG2](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/generic-paradigm/imgs/img2.png)

* 2、使用一个配置文件，程序在运行时要通过配置文件读取相关内容，可以在不修改源代码的前提下实现项目的变更
    - 当使用配置文件之后，虽然代码的维护方便了，在开发中不是很方便，非专业人士很难修改
    - 配置文件数量可能非常庞大，可能使得开发者迷惑

![IMG3](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/advanced_develop/generic-paradigm/imgs/img3.png)

* 3、将配置写回到程序中，形成了注解的概念
    - 写了注解不代表不需要写配置文件
    - JDK内置的注解
        + @Override
        + @Deprecated
        + @SuppressWarnings

### @Override（准确覆写）

* 方法覆写：发生在继承关系中，子类定义了与父类的方法名称相同，参数个数与类型，返回值类型相同的方法
* @Override检测覆写是否成功


### @Deprecated(过期声明)

* 从某个特定的版本之后，不在使用该程序类


### @SuppressWarnings(压制警告)

### 接口定义加强（JDK1.8新特性）

* 可以使用default来定义普通方法,需要对象调用
* 可以使用static定义静态方法，需要接口名就能调用




### lambda表达式

* Lambda从jdk1.8开始推出的重要新特性，函数式编程
* 使用匿名内部类实现接口优点是节约一个文件
* 面向对象要求结构必须完整 
* @FunctionalInterface表示一个函数式编程的接口，只允许有一个方法











