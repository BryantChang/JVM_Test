package iostream;

import java.io.*;
import java.util.Date;

/**
 * Created by bryantchang on 2017/7/9.
 * 字节流&&字符流
 * 字节流和字符流的本质区别只有一个,字节流是原生的操作,字符流是经过处理的操作
 * 保存的数据只能是字节,内存能够将字节变为字符
 * 字节流操作类:InputStream OutputStream
 * 字符流操作类:Reader Writer
 *
 * 操作流程:
 * 1、创建File类对象
 * 2、根据字节流或字符流的子类实例化父类对象
 * 3、进行数据的读取或写入操作
 * 4、关闭流
 * I/O操作是资源处理,最后必须关闭
 *
 * 字节输出流 OutputStream 抽象类实现了
 * Closeable && Flushable
 * Flushable:flush():void
 * Closeable:close():void
 * OutputStream自身方法
 * write方法
 * write(byte[])--将给定字节数组内容全部输出
 * write(byte[],int off,int len)将部分字节数组内容输出
 * write(int b)输出单个字节(抽象方法)
 *
 * FileOutputStream(File, boolean append)
 * 接收File类
 * 追加内容(append参数 为true表示追加)
 * 换行\r\n
 *
 * 自动关闭处理AutoCloseable接口
 * 如果想用,必须结合try--catch
 * 必须在try语句里面定义对象
 *
 * 字节输入流InputStream
 * 只实现了Closeable接口
 * 方法:
 * read(byte[])--读取数据到字节数组之中,返回读取个数,如果此时开辟的数组大小大于数据大小,则返回数据大小,小于是就是数组的长度
 * read(byte[],int off,int len)读取部分数据到字节数组之中
 * read()读取单个字节
 *
 * 字符流(适合处理中文)
 * 字符输出流Writer抽象类
 * Closeable && Flushable && Appendable
 * 提供write方法,接收的都是char型
 * FileWriter子类
 *
 * 字符输入流Reader(抽象类)
 * 实现Readable
 *
 * flush()强制刷新缓冲内容。
 *
 * 转换流
 * OutputStreamWriter将字节输出流转换为字符输出流
 * InputStreamReader将字节输入流转换为字节输入流
 *
 * OutputStreamWriter类 -- Writer类的子类
 * 构造要传入OutputStream对象
 *
 * 从继承结构上来讲,字符流处理的所有数据都是经过转换的
 */
interface IFruit{
    public void eat();
}

class Factory {
    public static IFruit getInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        IFruit obj = (IFruit)Class.forName(className).newInstance();
        return obj;
    }
}

class Apple implements IFruit {
    public void eat() {
        System.out.println("eat apple");
    }
}

class Orange implements IFruit {
    public void eat() {
        System.out.println("eat orange");
    }
}

public class TestDemo {
    public static void main(String[] args) {
        IFruit fruit = null;
        try {
            fruit = Factory.getInstance(Orange.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        fruit.eat();
    }
}
