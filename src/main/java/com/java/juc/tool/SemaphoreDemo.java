package com.java.juc.tool;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//6台車，停3格停車格
public class SemaphoreDemo {
    public static void main(String[] args) {
        //創建Semaphore 設置許可數量3個車位
        Semaphore semaphore = new Semaphore(3);

        //模擬有6台車
        for (int i = 1; i <=6; i++) {
            new Thread(()->{
                try {
                    //搶車位(許可證)
                    semaphore.acquire();
                    //車子停車
                    System.out.println(Thread.currentThread().getName()+" 搶到了車位");

                    //模擬停車時間
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    //車子離開
                    System.out.println(Thread.currentThread().getName()+" -----離開了車位");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    //釋放
                    semaphore.release();
                }
            }, i+"").start();
        }
    }
}
