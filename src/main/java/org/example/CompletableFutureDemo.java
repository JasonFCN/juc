package org.example;

import java.util.concurrent.*;

public class CompletableFutureDemo {
    static ExecutorService threadPool1 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(() -> {
//            return "Hello" + 1/0;
//        });
//        CompletableFuture<String> greetingFuture = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return "World";});
//
//        // c1 then c2
////        CompletableFuture<String> combinedFuture = helloFuture.thenCombine(greetingFuture, (m1, m2) -> m1 + " " + m2);
////        System.out.println(combinedFuture.get());
//        Void join = CompletableFuture.allOf(helloFuture, greetingFuture).join();
//        System.out.println(join);

        for (int i = 0; i < 10; i++) {
            new Thread(CompletableFutureDemo::doGet).start();
        }
        System.out.println("end");

    }

    private static void doGet() {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            //do sth
            return CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("sub task: child");
                return "child";
            }, threadPool1).join();//子任务
        }, threadPool1);
        System.out.println("main task: " + cf1.join());
    }
}
