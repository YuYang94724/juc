package com.java.juc.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer> {

    //拆分的時候兩個值不能超過10
    private static final Integer VALUE = 10;
    //拆分開始值
    private int begin;
    //拆分結束值
    private int end;
    //返回結果
    private int result;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        //判斷兩個相加的值是否大於10
        if ((end - begin) <= VALUE) {
            //相加
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            //繼續拆分
            int middle = (begin + end) / 2;
            //拆分左邊
            MyTask myTask1 = new MyTask(begin, middle);
            //拆分左邊
            MyTask myTask2 = new MyTask(middle+1, end);
            //調用方法拆分
            myTask1.fork();
            myTask2.fork();
            //合併結果
            result = myTask1.join() + myTask2.join();
        }
        return result;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(1, 100);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        //獲取最終合併結果
        Integer result = forkJoinTask.get();
        System.out.println(result);
        //關閉分支池對象
        forkJoinPool.shutdown();
    }
}
