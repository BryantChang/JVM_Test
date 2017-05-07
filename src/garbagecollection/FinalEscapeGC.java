package garbagecollection;

/**
 * Created by bryantchang on 2017/4/29.
 */
public class FinalEscapeGC {
    public static FinalEscapeGC SAVE_HOOC = null;
    public void isAlive() {
        System.out.println("yes, I am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed");
        FinalEscapeGC.SAVE_HOOC = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOC = new FinalEscapeGC();

        SAVE_HOOC = null;
        System.gc();

        Thread.sleep(500);
        if(SAVE_HOOC != null) {
            SAVE_HOOC.isAlive();
        }else {
            System.out.println("no, I am dead :(");
        }


        SAVE_HOOC = null;
        System.gc();

        Thread.sleep(500);
        if(SAVE_HOOC != null) {
            SAVE_HOOC.isAlive();
        }else {
            System.out.println("no, I am dead :(");
        }

    }
}
