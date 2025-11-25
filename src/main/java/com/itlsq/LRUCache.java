package com.itlsq;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K,V>extends LinkedHashMap<K,V> {
    private final int capacity;

    public LRUCache(int capacity){
        super(capacity,0.75f,true);
        this.capacity=capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest){
        return size()>capacity;
    }

    public static void main(String[] args){
        LRUCache<Integer,Integer> lru=new LRUCache<>(2);
        lru.put(1,1);
        lru.put(2,2);
        lru.get(1);
        lru.put(3,3);
        System.out.println(lru.keySet());
    }
}
