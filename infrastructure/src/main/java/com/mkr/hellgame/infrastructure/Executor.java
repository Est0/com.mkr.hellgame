package com.mkr.hellgame.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executor {
    private Configuration configuration;
    private ExecutorService executorService;
    private static Logger logger = LoggerFactory.getLogger(Executor.class);

    public Executor(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() {
        logger.info("Starting Executor...");
        executorService = Executors.newCachedThreadPool();
        executorService.submit(new RootTimerService(executorService, configuration.getJobRunStrategy(), configuration.getExecutorGranularity(), configuration.getTriggers()));
    }

    public boolean stop(long timeout, TimeUnit unit) throws InterruptedException {
        logger.info("Stopping Executor...");
        executorService.shutdownNow();
        return executorService.awaitTermination(timeout, unit);
    }
}
