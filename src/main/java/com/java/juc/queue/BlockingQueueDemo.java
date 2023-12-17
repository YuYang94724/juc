package com.java.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    //queue 先進先出
    private static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
    public static void main(String[] args) throws InterruptedException {
        //第一組方法測試 會拋出異常
        new BlockingQueueDemo().testGroup1();

        //第二組方法測試 不會拋出異常
        new BlockingQueueDemo().testGroup2();

        //第三組方法測試 會一直等待queue有位置再放入
        new BlockingQueueDemo().testGroup3();

        //第四組 設定超時
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("w", 3L, TimeUnit.SECONDS));//三秒後放不進去就放棄
    }

    public void testGroup1(){
        //第一組方法測試 會拋出異常
        System.out.println(blockingQueue.add("a"));//加入元素，若加入成功則返回true
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        System.out.println(blockingQueue.element());//返回queue第一個元素為誰(不取出)，若queue為empty則拋異常
        //System.out.println(blockingQueue.add("w"));//再放入超出長度就會拋異常
        System.out.println(blockingQueue.remove());//先進先出一個一個拿出
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //System.out.println(blockingQueue.remove());//再取出已經為空的queue就會拋異常
    }

    public void testGroup2(){
        //第二組方法測試 不會拋出異常
        System.out.println(blockingQueue.offer("a")); //加入元素，若加入成功則返回true
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        //System.out.println(blockingQueue.offer("www"));//再放入超出長度返回false
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //System.out.println(blockingQueue.poll());//已經為空再取出則返回null
    }

    public void testGroup3() throws InterruptedException {
        //第三組方法測試 會一直等待queue有位置再放入
        //put方法有異常要拋出
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        //blockingQueue.put("d");//queue滿了，再放入會一直waiting直到可以放
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        //System.out.println(blockingQueue.take());//queue空了，會一直waiting直到有東西可取
    }
}
