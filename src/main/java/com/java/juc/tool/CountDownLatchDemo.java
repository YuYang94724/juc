package com.java.juc.tool;

import java.util.concurrent.CountDownLatch;

//測試CountDownLatch
public class CountDownLatchDemo {

    //6位同學離開之後，班長才能關門
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        //6個同學離開 類似於--計數器，當數量到達0則執行接下去的方法 否則一直等待
        for (int i = 1; i < 7; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" 號同學離開了教室");
                countDownLatch.countDown();
            }, i+"").start();
        }

        //等待
        countDownLatch.await();
        //等待上述執行完畢才執行
        System.out.println(Thread.currentThread().getName()+" 班長鎖門了");
    }
}
