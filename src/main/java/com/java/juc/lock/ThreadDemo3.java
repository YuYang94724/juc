package com.java.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource {
    //定義旗幟
    private int flag = 1;// aa=1 bb=2 cc=3
    //創建鎖
    private Lock lock = new ReentrantLock();
    //創造三個Condition分別使用
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(int round) throws InterruptedException {
        //1. 先上鎖
        lock.lock();
        try {
            //3. 當不是1則await
            while (flag != 1) {
                c1.await();
            }
            //4. 是1則開始工作
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " round :" + round);
            }
            //5. 工作完則通知下一個人工作
            flag = 2;
            c2.signal();
        } finally {
            //2. 開鎖
            lock.unlock();
        }
    }

    public void print10(int round) throws InterruptedException {
        //1. 先上鎖
        lock.lock();
        try {
            //3. 當不是2則await
            while (flag != 2) {
                c2.await();
            }
            //4. 是2則開始工作
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " round :" + round);
            }
            //5. 工作完則通知下一個人工作
            flag = 3;
            c3.signal();
        } finally {
            //2. 開鎖
            lock.unlock();
        }
    }

    public void print15(int round) throws InterruptedException {
        //1. 先上鎖
        lock.lock();
        try {
            //3. 當不是3則await
            while (flag != 3) {
                c3.await();
            }
            //4. 是3則開始工作
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " round :" + round);
            }
            //5. 工作完則通知下一個人工作
            flag = 1;
            c1.signal();
        } finally {
            //2. 開鎖
            lock.unlock();
        }
    }
}

public class ThreadDemo3 {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                try {
                    shareResource.print5(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AA").start();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                try {
                    shareResource.print10(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "BB").start();

        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                try {
                    shareResource.print15(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "CC").start();
    }
}
