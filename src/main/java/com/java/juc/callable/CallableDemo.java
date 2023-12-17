package com.java.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class MyThread2 implements Callable {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+ " come in callable");
        return 200;
    }
}
public class CallableDemo {
    public static void main(String[] args) throws Exception {
        //傳送實例
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());
        //lambda寫法
        FutureTask<Integer> futureTask2 = new FutureTask<>(()->{
            System.out.println(Thread.currentThread().getName()+ " come in callable");
            return 1024;
        });
        //放入Thread使用
        new Thread(futureTask2, "nick").start();
        new Thread(futureTask1, "ariel").start();

//        while (!futureTask2.isDone()){
//            System.out.println("wait....");
//        }
        //使用FutureTask的get方法獲取回傳value
        System.out.println(futureTask2.get());

        System.out.println(futureTask1.get());

        System.out.println(Thread.currentThread().getName()+" come over");
    }
}
