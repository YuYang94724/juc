package com.java.juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        //創建5個thread的pool
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);//5個窗口
        //一個thread的pool
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
        //可自行新增及銷毀thread數量的pool
        ExecutorService threadPool3 = Executors.newCachedThreadPool();

        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
        threadPoolDemo.execute(threadPool1);
        System.out.println("========================");
        threadPoolDemo.execute(threadPool2);
        System.out.println("========================");
        threadPoolDemo.execute(threadPool3);
    }


    public void execute(ExecutorService threadPool){
        //10個顧客請求
        try {
            for (int i = 1; i <=10 ; i++) {
                //threadPool執行的方式
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+" 辦理業務");
                });
            }
        }catch (Exception e){

        }finally {
            //關閉池子
            threadPool.shutdown();
            System.out.println("ThreadPool shutDown!");
        }
    }
}

