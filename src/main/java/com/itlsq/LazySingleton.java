package com.itlsq;


public class LazySingleton {
    // 1. 声明实例变量，必须使用 volatile 关键字
    // volatile 作用：保证可见性，禁止指令重排序（防止对象初始化未完成就返回）
    private static volatile LazySingleton instance;

    // 2. 私有化构造方法
    private LazySingleton() {
    }

    // 3. 提供全局访问点
    public static LazySingleton getInstance() {
        // 第一层检查：如果 instance 已经存在，直接返回，避免不必要的同步锁消耗
        if (instance == null) {
            // 同步代码块，保证线程安全
            synchronized (LazySingleton.class) {
                // 第二层检查：防止多个线程同时通过了第一层检查后重复创建
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LazySingleton s = LazySingleton.getInstance();
                System.out.println(Thread.currentThread().getName() + " 拿到的实例: " + s);
            }).start();
        }
    }
}