package memorymanagement;

/**
 * String de intern()方法测试
 * 当前jdk版本1.8
 * intern()方法用途,检测常量池(符号引用)中是否已经包含一个等于此String对象的字符串
 * 若有则直接返回包含该字符串字面量的引用,若没有则将该字符串,则将此String对象
 * 包含的字符串添加到常量池中,返回次String对象的引用
 * 当jdk1.6之前的版本本代码会显示false,false
 * 原因是1.6版本的jdk中intern的实现方法是将首次遇到的字符串实例复制到永久代
 * 返回的也是复制后的引用,显然不是一个所以第一个会显示false
 * 在jdk1.7以后,intern函数遇到首次出现的字符串则不会再复制实例,而是会在常量池中
 * 直接记录这个对象的引用,于是这两个String实际是同一个引用,因此返回True
 * 后一个显示false的理由基本相同,引用不同
 */

public class StringInternTest {
    public static void main(String[] args) {
        String str1 = new StringBuilder("sca").append("la").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
