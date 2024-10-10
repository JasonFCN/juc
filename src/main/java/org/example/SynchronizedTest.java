package org.example;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class SynchronizedTest {
    public synchronized void sync(){
        printClassLayout(this, Thread.currentThread().getName() + "线程进入同步方法之后: ");
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        printClassLayout(synchronizedTest, "锁对象初始化之后: ");
        synchronizedTest.sync();
        TimeUnit.SECONDS.sleep(1);
        Thread thread = new Thread(synchronizedTest::sync);
        thread.start();
        TimeUnit.SECONDS.sleep(1);
    }

    private static void printClassLayout(Object obj, String description) {
        System.out.println(description);
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
