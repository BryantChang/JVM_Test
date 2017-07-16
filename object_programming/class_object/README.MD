## 类与对象

### 对象的产生分析

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

