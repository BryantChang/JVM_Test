package threadlocal;

/**
 * Created by bryantchang on 2017/7/31.
 */

class Message1 {
    private String note;
    public void setNote(String note) {
        this.note = note;
    }
    public String getNote() {
        return note;
    }
}

class MessageConsumer1 {
    public void print() { //明确接收Message类对象
        System.out.println(Thread.currentThread().getName() + "=" + MyUtil1.message.getNote());
    }
}

class MyUtil1 {
    public static Message1 message;
}
public class Test {
    public static void main(String[] args) {
        new Thread(() -> {
            Message1 msg = new Message1();
            msg.setNote("aaa");
            MyUtil1.message = msg;
            new MessageConsumer1().print();
        }, "A").start();

        new Thread(() -> {
            Message1 msg = new Message1();
            msg.setNote("bbb");
            MyUtil1.message = msg;
            new MessageConsumer1().print();
        }, "B").start();
    }
}
