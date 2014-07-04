package com.mkr.hellgame.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**This class is not thread safe!*/
public class Executor {
    private Configuration configuration;
    private ExecutorService executorService;
    private static Logger logger = LoggerFactory.getLogger(Executor.class);

    public Executor(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() throws ExecutorAlreadyStartedException {
        logger.trace("Enter method start()");
        if (executorService != null) {
            throw new ExecutorAlreadyStartedException();
        }

        logger.info("Starting Executor...");
        executorService = configuration.getExecutorService();
        executorService.submit(new RootTimerService(executorService, configuration.getJobRunStrategy(), configuration.getExecutorGranularity(), configuration.getTriggers()));
        logger.trace("Exit method start()");
    }

    public boolean stop(long timeout, TimeUnit unit) throws ExecutorAlreadyStoppedException, ExecutorNotStartedYetException {
        logger.trace("Enter method stop(long, TimeUnit)");
        if (executorService == null) {
            throw new ExecutorNotStartedYetException();
        }
        if (executorService.isShutdown()) {
            throw new ExecutorAlreadyStoppedException();
        }

        logger.info("Stopping Executor...");
        executorService.shutdownNow();
        boolean result;
        try {
            result = executorService.awaitTermination(timeout, unit);
        } catch (InterruptedException e) {
            result = false;
        }
        logger.trace("Exit method stop(long, TimeUnit)");
        return result;
    }
}
