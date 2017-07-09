/**
 * Created by bryantchang on 2017/7/8.
 * 在类使用的时候进行定义
 * 通配符
 * 可以接受所有的泛型类型,但又不能让用户任意修改
 * ?extends类:设置泛型上限,?extends Number  ---用在参数上
 * ?super类:设置泛型下限 --- 用在方法声明上
 */

class Point<T> {//T表示参数,是一个占位的标记
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
class Message<T> {
    private T note;

    public T getNote() {
        return note;
    }

    public void setNote(T note) {
        this.note = note;
    }
}
public class fanxing {
    public static void main(String[] args) {
        Message<Double> msg = new Message<>();
        msg.setNote(12.1);
        fun(msg);

    }

    public static void fun(Message<? extends Number> tmp) {
        System.out.println(tmp.getNote());
    }//可以接受任意类型,但无法修改
}
