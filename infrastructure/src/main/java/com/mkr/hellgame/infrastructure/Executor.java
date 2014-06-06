package com.mkr.hellgame.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Executor {
    private long granularity;
    private ExecutorService executorService;
    private Map<Trigger, Long> nextScheduledExecutionIns = new HashMap<Trigger, Long>();
    private Map<Trigger, Semaphore>
    private Map<Job, Boolean> jobStatuses = new HashMap<Job, Boolean>();

    public Executor(long granularity) {
        this.granularity = granularity;
    }

    public long getGranularity() {
        return granularity;
    }

    public void start() {
        executorService = Executors.newCachedThreadPool();
        executorService.submit(new InternalExecutor()
                new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (final Map.Entry<Trigger, Long> nextScheduledExecutionIn: nextScheduledExecutionIns.entrySet()) {
                        if (nextScheduledExecutionIn.getValue() <= 0) {
                            executorService.submit(new Runnable() {
                                @Override
                                public void run() {
                                    nextScheduledExecutionIn.getKey().getJob().run();
                                }
                            });
                            nextScheduledExecutionIn.setValue(nextScheduledExecutionIn.getKey().nextScheduledExecuteIn());
                        }
                    }

                    try {
                        Thread.sleep(granularity);
                    }
                    catch (InterruptedException e) {
                        return;
                    }

                    for (Map.Entry<Trigger, Long> nextScheduledExecutionIn: nextScheduledExecutionIns.entrySet()) {
                        nextScheduledExecutionIn.setValue(nextScheduledExecutionIn.getValue() - granularity);
                    }
                }
            }
        });

    }

    public boolean stop(long timeout, TimeUnit unit) {
        executorService.shutdown();
        try {
            return executorService.awaitTermination(timeout, unit);
        }
        catch (InterruptedException e) {
            return false;
        }
    }
}
