package com.mkr.hellgame.infrastructure;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

class TriggerExecutor implements Runnable {
    private ExecutorService executorService;
    private JobRunStrategy jobRunStrategy;
    private Trigger trigger;
    private Semaphore ready;
    private Semaphore awake;
    private AtomicLong nextExecutionIn;

    public TriggerExecutor(ExecutorService executorService, JobRunStrategy jobRunStrategy, Trigger trigger, Semaphore ready, Semaphore awake, AtomicLong nextExecutionIn) {
        this.executorService = executorService;
        this.jobRunStrategy = jobRunStrategy;
        this.trigger = trigger;
        this.ready = ready;
        this.awake = awake;
        this.nextExecutionIn = nextExecutionIn;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                awake.acquire();
                jobRunStrategy.run(executorService, trigger.getJob());
                nextExecutionIn.set(trigger.calcNextScheduledExecuteIn());
                ready.release();
            }
            catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Interrupted Trigger Executor");
    }
}
