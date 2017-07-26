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












