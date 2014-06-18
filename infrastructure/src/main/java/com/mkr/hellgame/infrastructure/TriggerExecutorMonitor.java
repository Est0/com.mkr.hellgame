package com.mkr.hellgame.infrastructure;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TriggerExecutorMonitor {
    //private Semaphore syncObj = new Semaphore(0);
    private Object signalObj = new Object();
    private Lock lock = new ReentrantLock();
    private volatile long timer;

    public TriggerExecutorMonitor(long timer) {
        this.timer = timer;
    }

    public void waitSignal() throws InterruptedException {
        //syncObj.acquire();
        signalObj.wait();
    }

    public void sendSignal() {
        //syncObj.release();
        signalObj.notify();
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long value) {
        lock.lock();
        try {
            timer = value;
        }
        finally {
            lock.unlock();
        }
    }

    public void decrementTimer(long value) {
        if (timer > 0 && lock.tryLock()) {
            try {
                timer -= value;
            }
            finally {
                lock.unlock();
            }
        }
    }
}
