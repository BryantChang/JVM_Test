package tongpeifu;

/**
 * Created by bryantchang on 2017/7/29.
 */


interface IMessage {
    public void print();
}

public class TestDemo {
    public static void main(String[] args) {
        IMessage msg = () -> System.out.println("Hello World");

    }
}
