package org.example;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class 生产者消费者Test {
    private static Object monitor = new Object();
    private static Queue<Integer> queue = new ArrayBlockingQueue<>(100, false);

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Producer());
        thread.start();
        Thread thread1 = new Thread(new Consumer());
        thread1.start();

        thread.join();
        thread1.join();
    }

    static class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                synchronized (monitor) {
                    while (queue.size() >= 100) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    queue.offer(i);
                    System.out.println("producer: " + i);
                    notify();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                synchronized (monitor) {
                    while (queue.size() == 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Integer poll = queue.poll();
                    System.out.println("consumer: " + poll);
                    notify();
                }
            }
        }
    }
}
