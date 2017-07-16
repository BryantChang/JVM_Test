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
内存图：
![IMG1](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/object_programming/class_object/imgs/img1.png)




