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


### 继承

* 继承的限制
    - 1、子类对象在实例化前，一定会首先实例化父类对象，默认调用父类构造方法后，再调用子类构造方法进行子类的实例化
    - 2、java只有单继承，不支持多继承

### 覆写

* 子类定义了与父类相同的属性或方法
    - 属性的覆盖
    - 方法的覆写

#### 方法覆写

* 方法覆写指的是子类定义了与父类方法名称，参数个数与类型完全相同的方法。被覆写的方法不能够拥有比父类更严格的访问权限
* 关注
    - 当前使用的对象是哪个类实例化的，优先是被覆写过的方法
    - private < default < public
    - 如果父类方法使用private定义，则该方法只能被父类所使用，子类不知道父类存在该方法。

### final关键字

* final被称为终结器
* final可以修饰类，方法，和常量（属性）
    - 使用final定义的类不能有子类（String）
    - 使用final定义的方法不允许被子类所覆写
    - 使用final定义的变量就是常量，常量必须在声明时赋值，不允许被修改，在开发过程中会使用public static final 来定义全局常量（不用每个对象都有一个）

### 多态性


* 1、方法的多态性
    - 方法的重载（同一个名称可以根据参数类型和个数的不同定义不同的方法体）
    - 方法的覆写（同一个父类的方法可能根据实例化的对象不同也有个不同的实现）
* 2、对象多态性（方法覆写）
    - 对象的向上转型：父类 父类对象 = 子类实例---有传递性没有限制
    - 对象的向下转型：子类 子类对象 = （子类）父类对象--当需要使用到子类扩充操作的时候
    - 不是所有的父类对象都可以向下转型--首先要发生向上转型，否则会发生ClassCastException（两个没有关系的类发生转换）

#### 举例（向上转型）

```java
class A {
    public void print() {
        System.out.println("print of A");
    }
}

class B extends A {
    public void print() {
        System.out.println("print of B");
    }
}

public class TestDemo {
    public static void main(String[] args) {
        A a = new B();//向上转型
        a.print();
    }
}
```

#### 举例（向下转型）

```java
class A {
    public void print() {
        System.out.println("print of A");
    }
}

class B extends A {
    public void print() {
        System.out.println("print of B");
    }

    public void funB() {
        System.out.println("funB")
    }
}

public class TestDemo {
    public static void main(String[] args) {
        A a = new B();//向上转型
        B b = (B) a;
        b.funB();
    }
}
```

* 直接进行向下转型可能存在隐患，需要首先进行判断，然后转型，利用instanceof关键字
* 子类对象 instanceof 类， 返回boolean类型数据

#### 举例(instanceof)

```java
class A {
    public void print() {
        System.out.println("print of A");
    }
}

class B extends A {
    public void print() {
        System.out.println("print of B");
    }

    public void funB() {
        System.out.println("funB")
    }
}

public class TestDemo {
    public static void main(String[] args) {
        A a = new B();//向上转型
        System.out.println(a instanceof A);
        System.out.println(a instanceof B);
        if(a instanceof B) {//避免ClassCastException
            B b = (B) a;
            b.funB();
        }
    }
}
```

* 对象多态性实现的核心是方法的覆写
* 向上转型可以实现接收参数的统一
* 向下转型可以实现子类方法的扩充
* 两个没有关系的类进行强制转换会出现ClassCastException

### 抽象类定义和使用

* 只声明而未实现的方法（抽象方法）--该方法所在的类需要使用abstract关键字修饰
* 所有的抽象方法不包含方法体
* 抽象类无法实例化--没有方法体，实例化对象可以调用类中的所有的操作
* 所有的抽象类必须有子类
* 抽象类的子类必须如果不是抽象类，则必须覆写父类中的所有抽象方法

### 抽象类的相关规定

* 抽象类中允许提供构造方法，子类实例化前也会先调用父类的构造方法。

#### 举例

```java
abstract class Person {
    private String name;
    private int age;
    public Person() {
        System.out.println(".....");
    }
    public abstract String getInfo();
}
class Student extends Person {
    private String school;
    pubic Student() {
        System.out.println("xxxxx");
    }
    public String getInfo() {
        return null;
    }
}

public class TestDemo {
    public static void main(String[] args) {
        new Student();
    }

}
```

* 因为抽象类中有属性，所有的属性要在对象实例化时进行空间的开辟，而实例化对象要依靠构造方法

#### 举例（流程梳理）

```java
abstract class A {
    public A() {//3、调用父类构造方法
        this.print(); //4、调用被子类覆写的方法
    }
    public abstract void print();
}

class B extends A {
    private int num = 100;//会开辟，但不会有值
    public B(int num) {//2、调用子类实例化对象
        super();//3、隐含一行语句，调用父类构造方法
        this.num = num;
    }

    public void print() {//由于此时子类构造还未执行，所以所有的属性都为各自类型对应的默认值
        System.out.println(this.num);
    }
}

public class TestDemo {
    ///结果为0 
    new B(30); //1、实例化子类对象
}
```

```
说明：
1、首先进行类加载
2、进行类对象的空间开辟
3、进行类对象的属性初始化（构造方法）
4、抽象类中可以没有任何抽象方法，但依然无法进行实例化
5、抽象类不能使用final进行声明
6、抽象方法不能用private定义
7、抽象类也分为内部抽象类和外部抽象类，内部抽象类可以使用static定义描述的是外部抽象类，如果外部抽象类上使用static被定义为语法错误
```

### 模板设计模式

* 抽象类的特点是强制定义了子类的实现结构
* 抽象类可以起到模板的作用


![IMG10](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img10.png)

#### 举例

```java
abstract class Action {
    public static final int EAT = 1;
    public static final int SLEEP = 5;
    public static final int WORK = 10;
    public abstract void eat();
    public abstract void sleep();
    public abstract void work();
    public void command(int cmd) {
        switch(cmd) {
            case EAT:
                this.eat();
                break;
            case SLEEP:
                this.sleep();
                break;
            case WORK:
                this.work();
                break; 
            case WORK+EAT+SLEEP:
                this.eat();
                this.sleep();
                this.work();
                break; 
        }
    }
}

class Human extends Action {
    public void eat(){
        System.out.println("Human eat");
    }
    public void sleep(){
        System.out.println("Human sleep");
    }
    public void work(){
        System.out.println("Human work");
    }
}

class Pig extends Action {
    public void eat(){
        System.out.println("Pig eat");
    }
    public void sleep(){
        System.out.println("Pig sleep");
    }
    public void work(){}
}

class Robot extends Action {
    public void eat(){
        System.out.println("Robot eat");
    }
    public void sleep(){}
    public void work(){
        System.out.println("Robot work");
    }
}

public class TestDemo {
    public static void main(String[] args) {
        fun(Human);
    }
    public static void fun(Action action) {
        action.command(Action.EAT + Action.SLEEP + Action.WORK);
    }
}
```

```
Note:
1、抽象类在实际的使用过程之中会定义一些固化的模式，它只会接收几种特定的指令
2、每种指令的具体实现由子类负责完成，父类只负责进行方法的约定
3、抽象类是单继承的，必须通过子类进行实例化处理
```



### 接口 ---抽象方法和全局常量的集合

* 避免单继承的局限
* 在今后的开发中接口优先
* 使用interface定义
* 使用implements关键字实现接口
* 一个子类可以实现多个接口，若其不是抽象类，则需要实现接口的所有抽象方法
* 利用向上转型实例化子类对象类得到接口的实例化对象
* 当一个子类实现了多个接口，并且接口对象通过子类进行实例化，那么这多个父接口之间是允许相互转换的


#### 接口转换示意图

![IMG11](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img11.png)

```java
interface IMessage {
    public static final String MSG = "Hello World";
    public void print();
}
interface INews {
    public String get();
}
class MessageImpl implements IMessage,INews {
    public void print(){
        System.out.println(IMessage.MSG);
    }

    public String get() {
        return IMessage.MSG;   //访问常量建议加上类名称  
    }
}
public class TestDemo {
    public static void main(String[] args) {
        INews m = new MessageImpl();
        IMessage ms = (IMessage) m;
        m.print();
    }
}
```


### 接口使用限制

* 接口中只允许存在public权限（不写默认public）
* 先使用extends 再用implements
* 抽象类可以实现接口，接口不能继承抽象类
* 



#### 举例(错误的覆写)

```java

interface INews {
    public String get();
}

class NewsImpl {
    String get() {//权限更加严格了，无法成功覆写
        return "hello world";  
    }
}

public class TestDemo {
    public static void main(String[] args) {
       
    }
}
```



#### 举例(继承抽象类并实现接口)

* 1：

```java

interface INews {
    public String get();
    public void print();
}

abstract class AbstractMessage implements INews{
    public abstract void print();
    public void print() {
        System.out.println("abstract");
    }
}


//重复标记
class NewsImpl extends AbstractMessage {
    public String get() {//权限更加严格了，无法成功覆写
        return "hello world";  
    }
    public void print(){}
}

public class TestDemo {
    public static void main(String[] args) {
       INews news = new NewsImpl();
       System.out.println(new.get());
       AbstractMessage am = (AbstractMessage) news;
       am.print();
    }
}
```


* 2：

```java

interface INews {
    public String get();
    public void print();
}

abstract class AbstractMessage implements INews{
    public abstract void print();
    public void print() {
        System.out.println("abstract");
    }
}


//重复标记
class NewsImpl extends AbstractMessage implements INews{
    public String get() {//权限更加严格了，无法成功覆写
        return "hello world";  
    }
    public void print(){}
}

public class TestDemo {
    public static void main(String[] args) {
       INews news = new NewsImpl();
       System.out.println(new.get());
       AbstractMessage am = (AbstractMessage) news;
       am.print();
    }
}
```

![IMG12](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img12.png)






