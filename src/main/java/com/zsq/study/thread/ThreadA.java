package com.zsq.study.thread;

public class ThreadA extends Thread {
    @Override
    public void run() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("treadA:"+this.getName());
    }

}
