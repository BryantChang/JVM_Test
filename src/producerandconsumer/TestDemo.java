package producerandconsumer;

/**
 * Created by bryantchang on 2017/7/8.
 * 经典的供求案例
 * 生产者负责生产数据,生产者生产完成后,消费者就要把数据取走
 * 生产者是一个线程对象,消费者是一个线程对象
 * DataProducer Data DataComsumer
 * 两类问题:
 *  数据不完整
 *  数据重复操作问题
 *
 * 数据同步问题:synchronized定义同步的操作方法
 * 等待唤醒机制Object类中提供的方法
 * wait等待  notify唤醒等待线程
 * 请解释sleep和wait的区别
 * sleep是Thread类的方法能够自动唤醒
 * wait是Object类的方法必须使用notify或notifyAll进行唤醒
 */


class Data {
    private boolean flag = false;//允许生产,不允许消费true
    private String title;
    private String note;

    public synchronized void set(String title, String note) {
        if(this.flag == true) { //现在不允许取走
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.title = title;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.note = note;
        this.flag = true;
        super.notify();
    }

    public synchronized void get() {
        if(flag == false) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.title + "=" + this.note);
        flag = false;//已经生产过了,不允许重复生产
        super.notify();
    }
}

class DataProducer implements Runnable {
    private Data data;

    public DataProducer(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            if(i % 2 == 0) {
                this.data.set("TitleA", "NoteA");
            }else {
                this.data.set("TitleB", "NoteB");
            }
        }
    }
}

class DataConsumer implements Runnable {
    Data data;

    public DataConsumer(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            this.data.get();
        }
    }
}

public class TestDemo {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(new DataProducer(data)).start();
        new Thread(new DataConsumer(data)).start();
    }
}
