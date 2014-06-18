package com.mkr.hellgame.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Executor {
    @Autowired
    private ExecutorConfigurationFactory executorConfigurationFactory;
    private ExecutorService executorService;

    private class InternalExecutor implements Runnable {
        private Map<Trigger, AtomicLong> nextScheduledExecutionIns = new HashMap<>();
        private Map<Trigger, Semaphore> triggerSyncObjects = new HashMap<>();

        @Override
        public void run() {
            Configuration configuration = executorConfigurationFactory.getConfiguration();
            long granularity = configuration.getExecutorGranularity();

            for (Trigger trigger: configuration.getTriggers()) {
                nextScheduledExecutionIns.put(trigger, new AtomicLong(trigger.calcNextScheduledExecuteIn()));
            }

            for (Trigger trigger: configuration.getTriggers()) {
                Semaphore syncObject = new Semaphore(0);
                triggerSyncObjects.put(trigger, syncObject);
                executorService.submit(new InternalTriggerExecutor(trigger, syncObject, nextScheduledExecutionIns.get(trigger)));
            }

            while (!Thread.currentThread().isInterrupted()) {
                for (Map.Entry<Trigger, AtomicLong> nextScheduledExecutionIn: nextScheduledExecutionIns.entrySet()) {
                    if (nextScheduledExecutionIn.getValue().get() <= 0) {
                        nextScheduledExecutionIn.getValue().set(Long.MAX_VALUE);
                        triggerSyncObjects.get(nextScheduledExecutionIn.getKey()).release();
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

    private class InternalTriggerExecutor implements Runnable {
        private Trigger trigger;
        private Semaphore syncObject;
        private AtomicLong nextExecutionIn;

        public InternalTriggerExecutor(Trigger trigger, Semaphore syncObject, AtomicLong nextExecutionIn) {
            this.trigger = trigger;
            this.syncObject = syncObject;
            this.nextExecutionIn = nextExecutionIn;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    syncObject.acquire();
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            trigger.getJob().run();
                        }
                    });
                    nextExecutionIn.set(trigger.calcNextScheduledExecuteIn());
                }
                catch (InterruptedException e) {
                    break;
                }
            }
            System.out.println("Interrupted Trigger Executor");
        }
    }

    public void start() {
        executorService = Executors.newCachedThreadPool();
        executorService.submit(new InternalExecutor());
    }

    public boolean stop(long timeout, TimeUnit unit) {
        executorService.shutdownNow();
        try {
            return executorService.awaitTermination(timeout, unit);
        }
        catch (InterruptedException e) {
            return false;
        }
    }
}
