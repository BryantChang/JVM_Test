package multithread;

/**
 * Created by bryantchang on 2017/7/30.
 */


class Data {
    private String title;
    private String note;

    private boolean flag = false; //true允许生产,但不允许取走 false 允许取走,不允许生产


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
        this.flag = false;//已经生产过了,不允许重复生产
        super.notify();
    }

}

class DataProvider implements Runnable {
    private Data data;
    public DataProvider(Data data) {
        this.data = data;
    }
    @Override
    public void run() {
        for (int x = 0; x < 50; x++) {
            if(x % 2 == 0) {
                this.data.set("titleA", "noteA");

            }else {
                this.data.set("titleB", "noteB");
            }
        }
    }
}

class DataConsumer implements Runnable {
    private Data data;
    public DataConsumer(Data data) {
        this.data = data;
    }
    @Override
    public void run() {
        for (int x = 0; x < 150; x++) {
            data.get();
        }
    }
}

public class DeadLock{
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(new DataProvider(data)).start();
        new Thread(new DataConsumer(data)).start();
    }
}
