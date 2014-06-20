package com.mkr.hellgame.infrastructure;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

class TriggerExecutorMonitor {
    private Semaphore awake = new Semaphore(0);
    private Semaphore ready = new Semaphore(1);
    private AtomicLong timer;

    public TriggerExecutorMonitor(long timer) {
        this.timer = new AtomicLong(timer);
    }

    public void waitAwakeSignal() throws InterruptedException{
        awake.acquire();
    }

    public void sendAwakeSignal() {
        awake.release();
    }

    public boolean isReady() {
        return ready.tryAcquire();
    }

    public void sendReadySignal() {
        ready.release();
    }

    public long getTimer() {
        return timer.get();
    }

    public void setTimer(long value) {
        timer.set(value);
    }

    public void decrementTimer(long value) {
        timer.getAndAdd(-value);
    }
}
