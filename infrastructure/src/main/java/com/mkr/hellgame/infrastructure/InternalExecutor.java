package com.mkr.hellgame.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

class InternalExecutor implements Runnable {
    @Autowired
    private Configuration configuration;

    private Map<Trigger, Long> nextScheduledExecutionIns = new HashMap<Trigger, Long>();

    @Override
    public void run() {
        for (Trigger trigger: configuration.getTriggers()) {
            nextScheduledExecutionIns.put(trigger, trigger.nextScheduledExecuteIn());
        }

        while (true) {


            try {
                Thread.sleep(configuration.getExecutorGranularity());
            }
            catch (InterruptedException e) {
                return;
            }

            for (Map.Entry<Trigger, Long> nextScheduledExecutionIn: nextScheduledExecutionIns.entrySet()) {
                nextScheduledExecutionIn.setValue(nextScheduledExecutionIn.getValue() - granularity);
            }
        }
    }
}
