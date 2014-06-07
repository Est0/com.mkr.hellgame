package com.mkr.hellgame.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class Executor {
    @Autowired
    private ExecutorConfigurationFactory executorConfigurationFactory;
    private ExecutorService executorService;

    private class InternalExecutor implements Runnable {
        private Map<Trigger, Long> nextScheduledExecutionIns = new HashMap<Trigger, Long>();

        @Override
        public void run() {
            Configuration configuration = executorConfigurationFactory.getConfiguration();
            long granularity = configuration.getExecutorGranularity();
            for (Trigger trigger: configuration.getTriggers()) {
                nextScheduledExecutionIns.put(trigger, trigger.nextScheduledExecuteIn());
            }

            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Tick-tack");
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
                    break;
                }

                for (Map.Entry<Trigger, Long> nextScheduledExecutionIn: nextScheduledExecutionIns.entrySet()) {
                    nextScheduledExecutionIn.setValue(nextScheduledExecutionIn.getValue() - granularity);
                }
            }
            System.out.println("Interrupted root timer");
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
