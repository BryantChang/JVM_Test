package annotation;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by bryantchang on 2017/8/5.
 */
@Retention(RetentionPolicy.RUNTIME)//次Annotation在运行时生效
@interface MyAnnotation {
    public String name() default "hello"; //定义了一个属性
    public int age() default 10;
}

@MyAnnotation
class Member implements Serializable {

}

public class TestDemo {
    public static void main(String[] args) {
        Annotation[] ant = Member.class.getAnnotations();
        for (int i = 0; i < ant.length; i++) {
            System.out.println(ant[i]);
        }
    }
}
