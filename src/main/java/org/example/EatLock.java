package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class EatLock implements Lock {
    private final Sync sync;
    private static final class Sync extends AbstractQueuedSynchronizer{
        private final int pCount;
        private static final int MAX_COUNT = 16;

        public Sync(int pCount) {
            if(MAX_COUNT < pCount){
                throw new Error("Maximum lock count exceeded");
            }
            this.pCount = pCount;
        }

        int getLeftForkShift(int id){
            return id - 1 < 0 ? pCount - 1 : id - 1;
        }
        int getRightForkShift(int id){
            return id;
        }
        int leftForkMask(int id){
            return  3 << getLeftForkShift(id);
        }

        int rightForkMask(int id){
            return 3 << getRightForkShift(id);
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

        @Override
        protected boolean tryAcquire(int arg) {
            int c = getState();
            int p = holdForkPeopleCount(c);
            boolean holdFork = isHoldFork(arg, c);
            if(p == pCount - 1 && !holdFork){
                return false;
            }
            if(!holdFork){
                return compareAndSetState(c, c + (1 << getLeftForkShift(arg)));
            }else{
                int leftFork = leftFork(arg, c);
                boolean holdForks;
                if(leftFork == UN_USED){
                    holdForks = compareAndSetState(c, c + (1 << getLeftForkShift(arg)));
                }else{
                    holdForks = compareAndSetState(c, c + (2 << getRightForkShift(arg)));
                }
                return holdForks;
            }
        }

        @Override
        protected boolean tryRelease(int arg) {
            return super.tryRelease(arg);
        }

        private int holdForkPeopleCount(int c) {
            int count = 0;
            for (int i = 0; i < pCount; i++) {
                boolean holdFork = isHoldFork(i, c);
                if(holdFork){
                    count++;
                }
            }
            return count;
        }

        private boolean isHoldFork(int i, int c) {
            return leftFork(i, c) == RIGHT_USED || rightFork(i, c) == LEFT_USED;
        }
    }

    public EatLock(int pCount) {
        sync = new Sync(pCount);
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
