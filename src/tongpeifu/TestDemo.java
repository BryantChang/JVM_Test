package tongpeifu;

/**
 * Created by bryantchang on 2017/7/29.
 */

@FunctionalInterface
interface IMessage {
    public void fun();
}



public class TestDemo {
    public static void main(String[] args) {
        IMessage msg = () -> System.out.println("Hello world");
        msg.fun();
    }
}
