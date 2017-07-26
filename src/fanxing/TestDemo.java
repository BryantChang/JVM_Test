package fanxing;

/**
 * Created by bryantchang on 2017/7/26.
 */

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
