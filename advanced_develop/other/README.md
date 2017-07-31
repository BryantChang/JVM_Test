## 其他知识点

### Runtime对象

* 每一个JVM进程中都会存在一个Runtime类的对象
* 构造方法私有化，因为其只能有一个实例化对象，该类使用的是单例设计模式(饿汉式)，在该类中有取得Runtime实例化对象的方法getRuntime()
* 可以通过Runtime观察当前的内存使用情况
    - freeMemory()取得所有空余内存空间
    - totalMemory()取得总空间大小
    - maxMemory()取得最大的可用空间

