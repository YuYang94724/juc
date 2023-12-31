package com.java.juc.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Share {
    private int number = 0;
    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public void incr() throws InterruptedException {
            lock.lock();
        try {
            //判斷
            while (number != 0){
                condition.await();
            }
            //做事情
            number++;
            System.out.println(Thread.currentThread().getName()+" :: "+number);
            //通知其他Thread
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
    public void decr() throws InterruptedException {
        lock.lock();
        try {
            //判斷
            while (number != 1){
                condition.await();
            }
            //做事情
            number--;
            System.out.println(Thread.currentThread().getName()+" :: "+number);
            //通知其他Thread
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}

public class ThreadDemo2 {
    public static void main(String[] args) {
        Share share = new Share();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AA").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "BB").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "CC").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "DD").start();
    }
}
