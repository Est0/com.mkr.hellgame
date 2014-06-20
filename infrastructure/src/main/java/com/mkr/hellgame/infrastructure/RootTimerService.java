package com.mkr.hellgame.infrastructure;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

class RootTimerService implements Runnable {
    private Map<Trigger, AtomicLong> nextScheduledExecutionIns = new HashMap<>();
    private Map<Trigger, Semaphore> triggerAwakeObjects = new HashMap<>();
    private Map<Trigger, Semaphore> triggerReadyObjects = new HashMap<>();
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
            nextScheduledExecutionIns.put(trigger, new AtomicLong(trigger.calcNextScheduledExecuteIn()));
            Semaphore awake = new Semaphore(0);
            triggerAwakeObjects.put(trigger, awake);
            Semaphore ready = new Semaphore(1);
            triggerReadyObjects.put(trigger, ready);
            executorService.submit(new TriggerExecutor(executorService, jobRunStrategy, trigger, ready, awake, nextScheduledExecutionIns.get(trigger)));
        }

        while (!Thread.currentThread().isInterrupted()) {
            for (Map.Entry<Trigger, AtomicLong> nextScheduledExecutionInEntry : nextScheduledExecutionIns.entrySet()) {
                Trigger trigger = nextScheduledExecutionInEntry.getKey();
                AtomicLong nextScheduledExecutionIn = nextScheduledExecutionInEntry.getValue();
                if (nextScheduledExecutionIn.get() <= 0 &&
                        triggerReadyObjects.get(trigger).tryAcquire()) {
                    triggerAwakeObjects.get(trigger).release();
                }
            }

            try {
                System.out.println("Tick-tack");
                Thread.sleep(granularity);
            }
            catch (InterruptedException e) {
                break;
            }

            for (Map.Entry<Trigger, AtomicLong> nextScheduledExecutionIn: nextScheduledExecutionIns.entrySet()) {
                nextScheduledExecutionIn.getValue().getAndAdd(-granularity);
            }
        }
        System.out.println("Interrupted root timer");
    }
}
