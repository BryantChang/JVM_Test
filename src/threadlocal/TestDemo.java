package threadlocal;

/**
 * Created by bryantchang on 2017/7/9.
 * ThreadLocal类
 * 可以减少引用传递
 * 明确的标记每一个线程的具体的对象信息,就需要使用ThreadLocal,多保存了一个currentThread
 * 重要方法:
 * get取得一个数据
 * set保存一个数据
 * remove删除数据
 */
class Message {
    private String note;



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

class MyUtil{
    public static ThreadLocal<Message> local = new ThreadLocal<>();
    public static void set(Message msg) {
        local.set(msg);
    }
    public static Message get() {
        return local.get();
    }
}

class MessageConsumer {
    public void print() {//consumer必须明确接受Message类对象
        System.out.println(Thread.currentThread().getName() + MyUtil.get().getNote());
    }
}
public class TestDemo {
    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.setNote("hello worlda");
                MyUtil.set(msg);
                new MessageConsumer().print();
            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.setNote("hello worldb");
                MyUtil.set(msg);
                new MessageConsumer().print();
            }
        }, "B").start();
    }
}
