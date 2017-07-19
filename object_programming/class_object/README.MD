## 类与对象

### 对象的产生分析

* 面向对象的三大特征：封装，继承，多态

#### 引用类型
* 引用类型，是指对内存空间的操作
    - 堆内存：保存真正的数据，保存对象的属性信息
    - 栈内存：保存堆内存的地址，堆内存的操作权（可以理解为保存对象名称）



### 举例(声明并实例化)：

```java
class Person {
    String name;
    int age;
    public void info() {
        System.out.println("name:" + name + ",age:" + age);
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Person per = new Person();
        per.name="aaa";
        per.age = 18;
        per.info();
    }
}
```
![IMG1](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img1.png)

### 举例(声明并实例化)：

```java
class Person {
    String name;
    int age;
    public void info() {
        System.out.println("name:" + name + ",age:" + age);
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Person per = null;
        per = new Person();
        per.name="aaa";
        per.age = 18;
        per.info();
    }
}
```
![IMG2](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img2.png)


```
所有引用数据类型必须在开辟空间后才能使用，如果使用了未开辟空间的引用数据类型，则会出现NullPointer异常（编译不会报错）
```

### 引用传递

* 引用传递的本质在于别名，而这个别名放在了栈内存，即一块对内存能够被多个栈内存所指向

#### 举例：

```java
class Person {
    String name;
    int age;
    public void info() {
        System.out.println("name:" + name + ",age:" + age);
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Person per1 = new Person();
        per1.name = "aaa";
        per.age = 18;
        //引用传递操作
        Person per2 = per1;
        per2.name = "bbb";
        per1.info();
    }
}
```

![IMG3](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img3.png)

```java
class Person {
    String name;
    int age;
    public void info() {
        System.out.println("name:" + name + ",age:" + age);
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Person per1 = new Person();
        Person per2 = new Person();
        per1.name = "aaa";
        per.age = 18;
        per2.name = "bbb";
        per2.age = 20;
        //引用传递操作
        per2 = per1;
        per2.name = "bbb";
        per1.info();
    }
}
```


![IMG4](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img4.png)


### 引用传递进阶

#### 举例1：
```java
class Message {
    private int num;
    public void setNum(int num) {
        this.num = num;
    }
    public int getNum() {
        return this.num;
    }

    public class TestDemo {
        public static void main(Stirng[] args) {
            Message msg = new Message();
            msg.setNum(100);
            fun(msg);
            System.out.println(msg.getNum());
        }

        public static void fun(Message tmp) {
            tmp.setNum(30);
        }
    }
}
```

![IMG5](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img5.png)


#### 举例2：

```java
class Message {
    private int num;
    public void setNum(int num) {
        this.num = num;
    }
    public int getNum() {
        return this.num;
    }

    public class TestDemo {
        public static void main(Stirng[] args) {
            String str = "hello";
            fun(str);
            System.out.println(str);///hello
        }

        public static void fun(String str) {
            tmp = "world";
        }
    }
}
```

* 字符串常量一旦声明则不可改变，字符串对象内容改变依靠的是地址引用关系变更


![IMG6](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img6.png)


#### 举例3：
```java
class Message {
    private String not;
    public void setNote(String note) {
        this.note = note;
    }
    public String getNote() {
        return this.note;
    }

    public class TestDemo {
        public static void main(Stirng[] args) {
            Message msg = new Message();
            msg.setNote("hello");
            fun(msg);
            System.out.println(msg.getNote());//world
        }

        public static void fun(Message tmp) {
            tmp.setNote("world");
        }
    }
}
```

![IMG7](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img7.png)


### 构造方法与匿名对象

* 构造方法指的是使用new实例化新对象时调用的方法
* 方法名称必须与类名称相同
* 类中的组成：属性，普通方法，构造方法
    - 属性是在对象开辟堆内存的时候
    - 构造方法是在使用关键字new时同时调用
    - 普通方法是对象实例化完毕后进行调用
* 构造方法的作用：
    - 可以利用构造方法设置类中的属性内容
    - 通过构造方法设置内容可以避免重复的setter调用
    - setter方法可以承担修改内容的操作
    - 构造方法依据参数的个数，采用升序或降序的方法排列
    - 第一写属性，第二写构造方法，第三写普通方法
* 匿名对象
    - 由于匿名对象不会有栈空间指向，使用一次后会成为垃圾

### this 关键字

* this调用属性--参数与属性同名问题
* this调用本类方法
    - 普通方法:this.method()
    - 构造方法:this(参数)--this调用必须是构造器的第一行语句，并且留有出口

#### 举例：
```java
class Person{
    private String name;
    private int age;
    public Person() {
        System.out.println("");  
    }
    public Person(String name) {
        this();
        this.name = name;
    }
}
```
* 表示当前对象
    - 在一个类中会产生若干个对象，当前对象表示正在操作本类方法的对象


### static 属性


####举例

```java

Class Person{
    String name;
    int age;
    String static country = "China";

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getInfo() {
        return this.name + "," + this.age + "," + this.country;
    }
}

Class TestDemo{
    public static void main(String[] args) {
        Person per1 = new Person("aaa", 10);
        Person per2 = new Person("bbb", 11);
        Person per3 = new Person("ccc", 12);
        System.out.println(per1.getInfo());
        System.out.println(per2.getInfo());
        System.out.println(per3.getInfo());
    }
}
```

![IMG8](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img8.png)

* 传统属性所具备的特征就是保存在堆内存中，每个对象独享此属性。
* 一次修改影响所有的对象。
* static关键字定义的属性将不保存在堆内存之中，将会保存在方法区（全局数据区）中，并且所有对象都可以进行该数据区的访问。

![IMG9](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img9.png)

* 共享属性不能通过对象进行修改，可以利用类名称直接调用

### static 方法

* 所有static方法不允许调用非static定义的属性或方法

### 代码块

* 使用大括号定义的一段程序代码，代码块根据其位置以及声明其关键字的不同，能够分为普通代码块，静态代码块，构造块，同步代码块。

* 普通代码块---定义在普通方法中

```java
public class TestDemo {
    public static void main(String[] args) {
        {
            int x = 10;
            System.out.println("x=" + x);
        }
        int x = 100;
        System.out.println("x=" + x);
    }
}
```

* 构造块---定义在类中的代码块，构造块优先于构造方法执行
* 可以进行一些简单的逻辑操作

```java
class Person {
    {
        System.out.println("构造块");
    }

    class Person(){
        System.out.println("构造方法");
    }
}
public class TestDemo {
    public static void main(String[] args){
        Person per1 = new Person();
        Person per2 = new Person();
    }
}

```

* 静态块--使用static关键字定义的代码块，分为两种情况
    - 在主类中定义：静态块优先于构造块执行，作用是为static属性初始化
    - 在主类中定义：静态块优先于主方法执行。

```java
class Person {
    {
        System.out.println("构造块");
    }

    Person(){
        System.out.println("构造方法");
    }
    static{
        System.out.println("静态块");
    }
}
public class TestDemo {
    public static void main(String[] args){
        Person per1 = new Person();
        Person per2 = new Person();
    }
}

```

### 内部类

* 一个类的内部嵌套其他类结构嵌套的操作语法形式
* 破坏了程序的结构
* 外部类的私有访问


```java
class Outer{
    private String = "Hello World";
    class Inner{
        public void print() {
            System.out.println(msg);
        }
    }
    public void fun() {
        Inner in = new Inner();
        in.print();
    }
}


public class TestDemo {
    public static void main(String args[]) {
        //声明内部类对象
        Outer.Inner in = new Outer().new Inner();
        in.print();
    }
}
```

* 外部类中存在有属性，只有实例化之后才能够被访问
* 如果一个内部类只想被外部类使用，不希望产生内部类的实例化对象，可以用private定义
* 如果在内部类中使用this，则需要使用外部类.this.属性名
* static定义的内部类，从功能上就是外部类，该内部类只能访问外部类的static操作，类名为“外部类.内部类”

#### 举例

```java
class Outer{
    private static String = "Hello World";
    static class Inner{//等同于外部类
        public void print() {
            System.out.println(msg);//此时只能引用外部类的static操作
        }
    }
    public void fun() {
        Inner in = new Inner();
        in.print();
    }
}


public class TestDemo {
    public static void main(String args[]) {
        //声明内部类对象
        Outer.Inner in = new Outer.Inner();
        in.print();
    }
}
```

### 在方法中定义内部类

* 在方法中定义内部类的形式是最多的

```java
class Outer{
    private String = "Hello World";
    public void fun(int num) {
        class Inner{
            public void print() {
                System.out.println("msg=" + msg);
                System.out.println("num=" + num);
            }
        }
        new Inner.print();
    }
}


public class TestDemo {
    public static void main(String args[]) {
        //声明内部类对象
        new Outer().print(100);
    }
}
```

* 在JDK1.8前是错误的，在JDK1.7及以前，如果一个内部类定义在了方法之中，该内部类要想访问方法中的参数，那么这个参数前必须使用final定义。
* 内部类的特点：
    - 破坏了程序的结构
    - 方便的进行私有属性的访问，内部访问外部，外部访问内部








