package com.java.juc.sync;


class Ticket {
    private int number = 30;

    public synchronized void sell() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() +
                    ": before = " + (number--) + ", after = " + number);
        }
    }
}

public class SaleTicket {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
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
