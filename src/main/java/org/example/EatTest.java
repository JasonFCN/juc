package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class EatTest {
    private final AtomicInteger forks = new AtomicInteger(0);
    static int getLeftForkShift(int id){
        return id - 1 < 0 ? 4 : id - 1;
    }
    static int getRightForkShift(int id){
        return id;
    }
    static int leftForkMask(int id){
        return  2 << getLeftForkShift(id);
    }

    static int rightForkMask(int id){
        return 2 << getRightForkShift(id);
    }
    static final int UN_USED = 0;
    static final int LEFT_USED = 2;
    static final int RIGHT_USED = 1;
    int leftFork(int id, int v){
        return v & leftForkMask(id) >> getLeftForkShift(id);
    }
    int rightFork(int id, int v){
        return v & rightForkMask(id) >> getRightForkShift(id);
    }

    public void eat(int id){
        for (int i = 0; i < 60; i++) {
            //尝试获取一把叉子
            for(;;){
                int v = forks.get();
                int left = leftFork(id, v);
                int right = rightFork(id, v);

                if(left == LEFT_USED || right == RIGHT_USED){
                    continue;
                }
                if(left == UN_USED && (right == LEFT_USED || right == UN_USED)){
                    // 获取左叉子
                }
                if(left == RIGHT_USED && right == UN_USED){
                    // 获取右叉子
                }

                if(canGetForks(id)){
                    for(;;){
                        // 获取叉子
//                        if(forks.compareAndSet() || forks.compareAndSet()){
//                            // 如果有两把叉子
//                            v = forks.get();
//                            if(leftFork(id, v) == RIGHT_USED && rightFork(id, v) == LEFT_USED){
//                                // 吃饭
//                                break;
//                            }
//                        }
                    }
                }
            }
        }
    }
    boolean canGetForks(int id){
        int v = forks.get();
        int left = leftFork(id, v);
        int right = rightFork(id, v);

        if(left == LEFT_USED || right == RIGHT_USED){
            return false;
        }
        return true;
    }

}
