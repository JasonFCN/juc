package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class FooBar {
    private int n;
    private final AtomicInteger mark = new AtomicInteger(0);

    public FooBar(int n) {
        this.n = n;
    }

    public static void main(String[] args) throws InterruptedException {
        FooBar fooBar = new FooBar(5);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    fooBar.foo(()-> System.out.print("foo"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                try {
                    fooBar.bar(()-> System.out.print("bar"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();
        thread2.start();
        TimeUnit.SECONDS.sleep(100);
    }



    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            int v;
            for(;;){
                if((v = mark.get()) != 1){
                    printFoo.run();
                    mark.compareAndSet(v, 1);
                    break;
                }
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n ; i++) {
            int v;
            for(;;){
                if((v = mark.get()) == 1){
                    // printBar.run() outputs "bar". Do not change or remove this line.
                    printBar.run();
                    mark.compareAndSet(v, 2);
                    break;
                }
            }
        }
    }
}
