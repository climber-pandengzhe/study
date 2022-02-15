package com.zsq.study.test;

import org.openjdk.jol.info.ClassLayout;

public class TestLock {

    public static void main(String[] args) {
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
