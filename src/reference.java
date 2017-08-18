/**
 * Created by bryantchang on 2017/8/7.
 */
public class reference {
    public static void main(String[] args) {
        Object obj = new Object();
        Object ref = obj;
        obj = null;
        System.gc();
        System.out.println(ref);
    }
}
