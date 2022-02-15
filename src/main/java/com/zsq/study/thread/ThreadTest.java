package com.zsq.study.thread;

public class ThreadTest {
    public static void main(String[] args) {
//        ThreadA a = new ThreadA();
//        a.setName("threadA");
//        a.start();
//        a.run();
//        System.out.println("main");


        Runnable runner = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() );
                try {
                    Thread.currentThread().wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"11" );
            }
        };

        new Thread(runner,"runner1").start();

        Thread.currentThread().notifyAll();

        System.out.println("----");

      //  new Thread(runner,"runner2").start();
    }
}
