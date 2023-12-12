package com.java.juc.sync;

class Share1 {
    private int number = 0;


    public synchronized void incr() throws InterruptedException {
        //判斷
        while (number != 0) {
            this.wait();
        }
        //做事情
        number++;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他Thread
        this.notifyAll();
    }

    public synchronized void decr() throws InterruptedException {
        //判斷
        while (number != 1) {
            this.wait();
        }
        //做事情
        number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        //通知其他Thread
        this.notifyAll();
    }
}

public class ThreadDemo1 {
    public static void main(String[] args) {
        Share1 share = new Share1();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "CC").start();
        new Thread(() -> {
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
