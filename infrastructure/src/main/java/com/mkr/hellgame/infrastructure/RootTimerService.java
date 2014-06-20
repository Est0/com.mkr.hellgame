package com.mkr.hellgame.infrastructure;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

class RootTimerService implements Runnable {
    private Map<Trigger, TriggerExecutorMonitor> triggerExecutorMonitors = new HashMap<>();
    private ExecutorService executorService;
    private JobRunStrategy jobRunStrategy;
    private long granularity;
    private Collection<Trigger> triggers;

    RootTimerService(ExecutorService executorService, JobRunStrategy jobRunStrategy, long granularity, Collection<Trigger> triggers) {
        this.executorService = executorService;
        this.jobRunStrategy = jobRunStrategy;
        this.granularity = granularity;
        this.triggers = triggers;
    }

    @Override
    public void run() {
        for (Trigger trigger: triggers) {
            TriggerExecutorMonitor triggerExecutorMonitor = new TriggerExecutorMonitor(trigger.calcNextScheduledExecuteIn());
            triggerExecutorMonitors.put(trigger, triggerExecutorMonitor);
            executorService.submit(new TriggerExecutor(executorService, jobRunStrategy, trigger, triggerExecutorMonitor));
        }

        while (!Thread.currentThread().isInterrupted()) {
            for (TriggerExecutorMonitor triggerExecutorMonitor : triggerExecutorMonitors.values()) {
                if (triggerExecutorMonitor.getTimer() <= 0 &&
                        triggerExecutorMonitor.isReady()) {
                    triggerExecutorMonitor.sendAwakeSignal();
                }
            }

            try {
                System.out.println("Tick-tack");
                Thread.sleep(granularity);
            }
            catch (InterruptedException e) {
                break;
            }

            for (TriggerExecutorMonitor triggerExecutorMonitor: triggerExecutorMonitors.values()) {
                triggerExecutorMonitor.decrementTimer(granularity);
            }
        }
        System.out.println("Interrupted root timer");
    }
}
