# Java内存模型

## Java运行模式

* Java程序执行流程
    - 真实的Java程序运行在运行时数据区中

![IMG1](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/memory_model/imgs/img1.png)

* Java运行时数据区：
    - 堆内存：保存所有引用类型的真实信息（线程共享）
    - 栈内存：基本类型、运算、指向堆内存的指针（线程私有）
    - 方法区：所有定义的方法信息都保存在方法区中，常量，属于共享区（线程共享）
    - 程序计数器：当前执行指令（线程私有）
    - 本地方法栈：每一次执行递归的方法处理的时候都会将上一个方法入栈

![IMG2](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/memory_model/imgs/img2.png)

* Java虚拟机栈

![IMG3](https://raw.githubusercontent.com/BryantChang/JVM_Test/master/memory_model/imgs/img3.png)

* 在Java之中存在有对象池的概念，对象池是对整个常量池的一个破坏，因为在JVM启动的时候，所有的常量都已经分配好空间，但String的intern()方法可以打破该限制，动态进行常量池的内容。
* 运行时数据区和线程对象有关联

## Java对象访问方式

* Java引用数据类型会牵扯，堆，栈，方法区。
