package com.itlsq;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

//原子类测试学习
public class AtomicExperiment {

    private static int unsafeCount = 0;
    private static AtomicInteger atomicCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 10;
        int incrementPerThread = 1000;

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < incrementPerThread; j++) {
                    unsafeCount++;
                    atomicCount.incrementAndGet();
                }
                latch.countDown();
            }).start();
        }

        latch.await();

        System.out.println("理论期望值: " + (threadCount * incrementPerThread));
        System.out.println("普通 int 结果: " + unsafeCount + " (通常小于期望值，存在竞态条件)");
        System.out.println("AtomicInteger 结果: " + atomicCount.get() + " (绝对准确)");

        System.out.println("----------");

        //CAS
        AtomicInteger balance = new AtomicInteger(100);
        boolean success1 = balance.compareAndSet(100, 200);
        System.out.println("第一次更新 (100 -> 200): " + success1 + ", 当前值: " + balance.get());

        boolean success2 = balance.compareAndSet(100, 300);
        System.out.println("第二次更新 (100 -> 300): " + success2 + ", 当前值: " + balance.get());

        boolean success3 = balance.compareAndSet(200, 300);
        System.out.println("第三次更新 (200 -> 300): " + success3 + ", 当前值: " + balance.get());


        System.out.println("----------");

        String ref = "A";
        AtomicStampedReference<String> stampedRef = new AtomicStampedReference<>(ref, 1);

        System.out.println("初始值: " + stampedRef.getReference() + ", 版本: " + stampedRef.getStamp());

        stampedRef.compareAndSet("A", "B", stampedRef.getStamp(), stampedRef.getStamp() + 1);
        stampedRef.compareAndSet("B", "A", stampedRef.getStamp(), stampedRef.getStamp() + 1);

        System.out.println("经过 ABA 操作后的值: " + stampedRef.getReference() + ", 当前版本: " + stampedRef.getStamp());

        boolean abaSuccess = stampedRef.compareAndSet("A", "C", 1, 2);

        System.out.println("尝试使用旧版本号更新 (期望 A->C): " + abaSuccess);

    }
}