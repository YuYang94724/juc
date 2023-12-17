package com.java.juc.lock;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ThreadSafeCollections {
    public static void main(String[] args) {

        //JUC提供Thread安全的
        List<String> list = new CopyOnWriteArrayList<>();
        Set<String> set = new CopyOnWriteArraySet<>();
        Map<String, String> map = new ConcurrentHashMap<>();
    }
}
