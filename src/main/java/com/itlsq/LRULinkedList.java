package com.itlsq;

import java.util.HashMap;
import java.util.Map;

public class LRULinkedList {
    private static class Node{
        int key,value;
        Node prev,next;
        Node(int key,int value){
            this.key=key;
            this.value=value;
        }
    }

    private final int capacity;
    private final Node dummy=new Node(0,0);
    private final Map<Integer,Node> keyToNode=new HashMap<>();

    public LRULinkedList(int capacity){
        this.capacity=capacity;
        dummy.next=dummy;
        dummy.prev=dummy;
    }

    public void remove(Node x){
        x.prev.next=x.next;
        x.next.prev=x.prev;
    }

    public void pushFront(Node x){
        x.prev=dummy;
        x.next=dummy.next;
        x.prev.next=x;
        x.next.prev=x;
    }

    public Node getNode(int key){
        if(!keyToNode.containsKey(key)){
            return null;
        }
        Node node=keyToNode.get(key);
        remove(node);
        pushFront(node);
        return node;
    }

    public int get(int key){
        Node node=getNode(key);
        return node!=null?node.value:-1;
    }

    public void put(int key,int value){
        Node node=getNode(key);
        if(node!=null){
            node.value=value;
            return;
        }
        node=new Node(key,value);
        keyToNode.put(key,node);
        pushFront(node);
        if(keyToNode.size()>capacity){
            Node backNode=dummy.prev;
            keyToNode.remove(backNode.key);
            remove(backNode);
        }

    }

    //ai生成测试用例
    public static void main(String[] args) {
        System.out.println("=== 开始测试 LRU 缓存 ===");

        // 1. 初始化容量为 2 的缓存
        LRULinkedList lru = new LRULinkedList(2);
        System.out.println("初始化容量: 2");

        // 2. 放入两个数据
        lru.put(1, 1);
        lru.put(2, 2);
        System.out.println("放入 (1,1) 和 (2,2)");

        // 验证基本获取
        int val1 = lru.get(1);
        System.out.println("获取 key=1: " + val1 + " (预期: 1)");
        // 此时缓存状态：[1, 2] -> 1 是最近使用的，2 是最久未使用的

        // 3. 放入第三个数据，触发淘汰
        lru.put(3, 3);
        System.out.println("放入 (3,3) -> 应该淘汰 key=2");
        // 此时缓存状态：[3, 1] -> 2 被淘汰，因为在第2步我们访问了1，所以1变成了最近使用，2变成了垫底

        // 4. 验证淘汰结果
        int val2 = lru.get(2);
        System.out.println("获取 key=2: " + val2 + " (预期: -1, 因为被淘汰了)");

        // 5. 验证更新逻辑
        lru.put(3, 333);
        System.out.println("更新 key=3 的值为 333");

        // 6. 再次放入导致淘汰
        lru.put(4, 4);
        System.out.println("放入 (4,4) -> 应该淘汰 key=1");
        // 之前的状态是 [3, 1]，3刚被更新过是热乎的，1是最冷的。所以1被淘汰。

        int val1_new = lru.get(1);
        int val3 = lru.get(3);
        int val4 = lru.get(4);

        System.out.println("获取 key=1: " + val1_new + " (预期: -1)");
        System.out.println("获取 key=3: " + val3 + " (预期: 333)");
        System.out.println("获取 key=4: " + val4 + " (预期: 4)");

        // 简单的自动判定
        boolean isPass = (val2 == -1) && (val1_new == -1) && (val3 == 333) && (val4 == 4);
        System.out.println("\n=== 测试结果: " + (isPass ? "通过 ✅" : "失败 ❌") + " ===");
    }
}
