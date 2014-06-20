package com.mkr.hellgame.infrastructure;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;

import java.util.concurrent.ExecutorService;

class TriggerExecutor implements Runnable {
    private ExecutorService executorService;
    private JobRunStrategy jobRunStrategy;
    private Trigger trigger;
    private TriggerExecutorMonitor triggerExecutorMonitor;

    public TriggerExecutor(ExecutorService executorService, JobRunStrategy jobRunStrategy, Trigger trigger, TriggerExecutorMonitor triggerExecutorMonitor) {
        this.executorService = executorService;
        this.jobRunStrategy = jobRunStrategy;
        this.trigger = trigger;
        this.triggerExecutorMonitor = triggerExecutorMonitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                triggerExecutorMonitor.waitAwakeSignal();
                jobRunStrategy.run(executorService, trigger.getJob());
                triggerExecutorMonitor.setTimer(trigger.calcNextScheduledExecuteIn());
                triggerExecutorMonitor.sendReadySignal();
            }
            catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Interrupted Trigger Executor");
    }
}
