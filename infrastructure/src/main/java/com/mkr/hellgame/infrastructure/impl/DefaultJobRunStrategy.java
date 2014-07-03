package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class DefaultJobRunStrategy implements JobRunStrategy {
    private static Logger logger = LoggerFactory.getLogger(DefaultJobRunStrategy.class);

    @Override
    public void run(ExecutorService executorService, Runnable job) {
        logger.info("DefaultJobRunStrategy called, starting job {}", job);
        if (executorService == null || job == null) {
            return;
        }

        executorService.submit(job);
    }
}
