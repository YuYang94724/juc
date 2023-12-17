package com.java.juc.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

//將writeLock降級為讀鎖
public class DownGradeWriteLock {

    public static void main(String[] args) {
        //可重入讀寫鎖對象
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

        //鎖降級 只有寫可以降級 讀鎖不能升級

        // 1 獲取寫鎖
        writeLock.lock();
        System.out.println("wLock locked");

        //2 獲取讀鎖
        readLock.lock();
        System.out.println("rLock locked");
    }
}
