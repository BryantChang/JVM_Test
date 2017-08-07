package producerandconsumer;

import java.util.LinkedList;
import java.util.Queue;
class Person {
    private int foodNum = 0;
    private Object sysObj = new Object();
    private final int MAX_NUM = 5;


    public void produce() throws InterruptedException {
        synchronized (sysObj) {
            while(foodNum == 5) {
                System.out.println("box is full, size= " + foodNum);
                sysObj.wait();
            }
            foodNum++;
            System.out.println(Thread.currentThread().getName() + "produce succ foodNum=" + foodNum);
            sysObj.notifyAll();
        }
    }

    public void consume() throws InterruptedException{
        synchronized (sysObj) {
            while(foodNum == 0) {
                System.out.println("box is empty, size= " + foodNum);
                sysObj.wait();
            }
            foodNum--;
            System.out.println(Thread.currentThread().getName() +  "consume succ foodNum = " + foodNum);
            sysObj.notifyAll();
        }

    }
}


class Producer implements Runnable {
    private Person person;
    private String producerName;

    public Producer(String producerName, Person person) {
        this.person = person;
        this.producerName = producerName;
    }

    public String getProducerName() {
        return producerName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                person.produce();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private Person person;
    private String consumerName;

    public String getConsumerName() {
        return consumerName;
    }

    public Consumer(String consumerName, Person person) {
        this.person = person;
        this.consumerName = consumerName;
    }

    @Override
    public void run() {
        while(true) {
            try {
                person.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class TestDemo {
    public static void main(String[] args) {
        Person person = new Person();
        new Thread(new Consumer("消费者一", person), "消费者一").start();
        new Thread(new Consumer("消费者二", person), "消费者二").start();
        new Thread(new Consumer("消费者三", person), "消费者三").start();

        new Thread(new Producer("生产者一", person), "生产者一").start();
        new Thread(new Producer("生产者一", person), "生产者二").start();
        new Thread(new Producer("生产者一", person), "生产者三").start();
    }
}
