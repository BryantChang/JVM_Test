package threadpool;

import java.util.concurrent.*;

/**
 * Created by bryantchang on 2017/7/9.
 * 线程池
 * 线程池中会有若干个线程对象
 * 两个核心接口(java.util.concurrent):
 * 普通线程池定义:ExecutorService
 * 调度线程池:ScheduledExecutorService
 * 一般可以使用Executor类创建线程池
 * 创建无大小限制的线程池newCachedThreadPool()
 * 创建固定大小的线程池newFixedThreadPool()
 * 单线程池newSingleThreadScheduledExecutor()
 * 创建定时调度线程池newScheduledThreadPool()
 *
 * 创建各种线程池
 */
public class TestDemo {
    public static void main(String[] args) {
        //创建了一个线程池的模型

        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(3);
        for (int x = 0; x < 10; x++) {
            final int index = x;
            threadPool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ",x=" + index);
                }
            },3, 2, TimeUnit.SECONDS);
        }
    }
}
