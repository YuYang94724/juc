package com.java.juc.tool;

import java.util.concurrent.CyclicBarrier;

//集齊七顆龍珠可以召喚神龍
public class CyclicBarrierDemo {
    //創建固定值
    public static final int NUMBER = 7;

    public static void main(String[] args) {
        //創建CyclicBarrier
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, ()->{
            System.out.println("*******集齊七顆龍珠可以召喚神龍");
        });

        //集齊七顆龍珠過程 類似於++計數器 數量到達你設定的值則執行方法 若無法滿足則一直等待
        for (int i = 1; i <=7 ; i++) {
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+ " 星球被收集到了");
                    //等待收集完畢
                    cyclicBarrier.await();
                }catch (Exception e){

                }
            }, i+"").start();
        }
    }
}
