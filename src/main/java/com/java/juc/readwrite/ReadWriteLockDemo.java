package com.java.juc.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//資源類
class MyCatch {

    //創建map volatile可以告訴邊義器可能會變多thread操作，有可見性保證
    private volatile Map<String, Object> map = new HashMap<>();
    //創建讀寫LOCK, writeLock是獨占鎖, readLock是共享鎖
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    //放數據
    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + " 正在做寫操作 " + key);
        //暫停一下
        try {
            TimeUnit.MICROSECONDS.sleep(300);
            //放數據
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 寫好了 " + key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            rwLock.writeLock().unlock();
        }
}

    public Object get(String key) {
        rwLock.readLock().lock();
        Object result = null;
        System.out.println(Thread.currentThread().getName() + " 正在讀取 " + key);
        //暫停一下
        try {
            TimeUnit.MICROSECONDS.sleep(300);
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 讀取好了 " + key);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            rwLock.readLock().unlock();
        }
        return result;
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCatch myCatch = new MyCatch();
        //創建Thread放數據
        for (int i = 1; i <=5; i++) {
            final int num = i;
            new Thread(()->{
                myCatch.put(num+"", num+"");
            }, i+"").start();
        }
        //創建Thread取數據
        for (int i = 1; i <=5; i++) {
            final int num = i;
            new Thread(()->{
                myCatch.get(num+"");
            }, i+"").start();
        }
    }
}
