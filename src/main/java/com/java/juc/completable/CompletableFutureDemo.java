package com.java.juc.completable;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception {
        //同步調用
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : CompletableFuture1");
        });
        //觸發
        completableFuture1.get();

        //異步調用
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " : CompletableFuture2");
            //模擬異常
//            int i = 10/0;
            return 1024;
        });
        //觸發
        completableFuture2.whenComplete((t, u) -> {
            System.out.println("-----t = " + t);
            System.out.println("-----u = " + u);
        });
    }
}
