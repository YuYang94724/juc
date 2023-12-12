package com.java.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LTicket{
    private int number = 30;
    public Lock lock = new ReentrantLock();

    public void sell() {
        try {
            lock.lock();
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() +
                        ": before = " + (number--) + ", after = " + number);
            }
        }finally {
            lock.unlock();
        }
    }

}
public class LSaleTicket {
    public static void main(String[] args) {
        LTicket ticket = new LTicket();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sell();
            }
        }, "AA").start();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sell();
            }
        }, "BB").start();
    }
}
