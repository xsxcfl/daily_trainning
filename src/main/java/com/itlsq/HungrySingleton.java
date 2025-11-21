package com.itlsq;


public class HungrySingleton {
    // 1. 在类加载时就直接创建实例（static final 保证不可变且全局唯一）
    private static final HungrySingleton INSTANCE = new HungrySingleton();

    // 2. 私有化构造方法，防止外部通过 new 创建对象
    private HungrySingleton() {
    }

    // 3. 提供全局访问点
    public static HungrySingleton getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {

        HungrySingleton singleton1 = HungrySingleton.getInstance();
        HungrySingleton singleton2 = HungrySingleton.getInstance();

        System.out.println("两个对象是否相同: " + (singleton1 == singleton2));
    }
}