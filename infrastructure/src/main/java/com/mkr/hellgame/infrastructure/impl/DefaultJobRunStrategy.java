package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.Job;
import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class DefaultJobRunStrategy implements JobRunStrategy {
    private static Logger logger = LoggerFactory.getLogger(DefaultJobRunStrategy.class);

    @Override
    public void run(ExecutorService executorService, final Job job) {
        logger.info("DefaultJobRunStrategy called, starting job {}", job);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                job.run();
            }
        });
    }
}
