package com.mkr.hellgame.infrastructure;

import com.mkr.hellgame.infrastructure.abstraction.ExecutorConfigurationFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executor {
    private ExecutorConfigurationFactory executorConfigurationFactory;
    private ExecutorService executorService;

    public Executor(ExecutorConfigurationFactory executorConfigurationFactory) {
        this.executorConfigurationFactory = executorConfigurationFactory;
    }

    public void start() {
        Configuration configuration = executorConfigurationFactory.getConfiguration();
        executorService = Executors.newCachedThreadPool();
        executorService.submit(new RootTimerService(executorService, configuration.getJobRunStrategy(), configuration.getExecutorGranularity(), configuration.getTriggers()));
    }

    public boolean stop(long timeout, TimeUnit unit) throws InterruptedException {
        executorService.shutdownNow();
        return executorService.awaitTermination(timeout, unit);
    }
}
