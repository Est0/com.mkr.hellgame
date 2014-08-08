package com.mkr.hellgame.infrastructure;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

class TriggerExecutor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(TriggerExecutor.class);
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
                logger.debug("TriggerExecutor awaken for Trigger {}", trigger);
                jobRunStrategy.run(executorService, trigger.getJob());
                triggerExecutorMonitor.setTimer(trigger.calcNextScheduledExecuteIn());
                logger.debug("TriggerExecutor falls asleep for Trigger {}", trigger);
                triggerExecutorMonitor.sendReadySignal();
            }
            catch (InterruptedException e) {
                break;
            }
        }
        logger.info("Interrupted TriggerExecutor");
    }
}
