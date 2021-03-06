package memorymanagement;

/**
 * 验证对象如果不为实例中的字段赋初值默认都为字段所对应类型的零值
 */

class ObjectTest {
    int propertyInt;
    String propertyString;
    ObjectTest(int p1, String p2) {
        this.propertyInt = p1;
        this.propertyString = p2;
    }
    ObjectTest(){}

    @Override
    public String toString() {
        return "p1:" + propertyInt + ", p2:" + propertyString;
    }
}
public class ObjectCreate {
    public static void main(String[] args) {
        ObjectTest test = new ObjectTest();
        System.out.println(test);
    }


}
