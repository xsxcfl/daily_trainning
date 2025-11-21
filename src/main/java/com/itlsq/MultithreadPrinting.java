package com.itlsq;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultithreadPrinting {
    private static final Logger logger = LoggerFactory.getLogger(MultithreadPrinting.class);

    //实现Runnable接口 基础并发打印
    public static class SimplePrint{
        public static void main(String[] args){
            Runnable printTask=()->{
                String threadName=Thread.currentThread().getName();
                for(int i=1;i<=5;i++){
                    System.out.println(threadName+"正在打印："+i);
                    try{
                        Thread.sleep((long)(Math.random()*500));
                    }catch (InterruptedException e){
                        logger.error("执行任务时发生异常", e);
                    }
                }
            };

            new Thread(printTask,"线程A").start();
            new Thread(printTask,"线程B").start();
        }

    }


    //两个线程交替打印
    public static class AlternatingPrint{
        private static final Object lock=new Object();
        private static int count=1;
        private static final int LIMIT=10;

        public static void main(String[] args){
            Runnable printTask=()->{
                while(true){
                    synchronized (lock){
                        if(count>LIMIT){
                            lock.notifyAll();
                            break;
                        }
                        System.out.println(Thread.currentThread().getName() + " 打印: " + count);

                        count++;
                        lock.notify();

                        try{
                            if(count<=LIMIT){
                                lock.wait();
                            }
                        }catch (InterruptedException e){
                            logger.error("执行任务时发生异常", e);
                        }
                    }
                }
            };

            new Thread(printTask,"奇数线程").start();
            new Thread(printTask,"偶数线程").start();
        }
    }

    //使用线程池
    public static class ThreadPrint{
        public static void main(String[] args){
            ExecutorService executor= Executors.newFixedThreadPool(3);

            for(int i=1;i<=5;i++){
                final int taskId=i;
                executor.submit(()->{
                    System.out.println(Thread.currentThread().getName() + " 正在处理任务 " + taskId);

                    try{
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        logger.error("执行任务时发生异常", e);
                    }
                });
            }

            executor.shutdown();
        }
    }

}
