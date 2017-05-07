package garbagecollection;

/**
 * Created by bryantchang on 2017/4/28.
 */
public class ReferenceCounting {
    private static final int MB = 1024*1024;
    public Object instance = null;

    private byte[] someMem = new byte[2 * MB];

    public static void testGC() {
        ReferenceCounting objA = new ReferenceCounting();
        ReferenceCounting objB = new ReferenceCounting();
        objA.instance = objB;
        objB.instance = objA;
        System.gc();
        System.out.println(objA);
    }



    public static void main(String[] args) {
        testGC();
    }
}
