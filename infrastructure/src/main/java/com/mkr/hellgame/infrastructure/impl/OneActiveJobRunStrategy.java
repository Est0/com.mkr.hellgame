package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

public class OneActiveJobRunStrategy implements JobRunStrategy {
    private static Logger logger = LoggerFactory.getLogger(OneActiveJobRunStrategy.class);
    private Map<Runnable, Future<?>> jobStatuses = new ConcurrentHashMap<>();
    private ConcurrentMap<Runnable, Object> jobSyncObjects = new ConcurrentHashMap<>();

    @Override
    public void run(ExecutorService executorService, Runnable job) {
        logger.info("OneActiveJobRunStrategy called for job {}", job);
        if (executorService == null || job == null) {
            return;
        }

        jobSyncObjects.putIfAbsent(job, new Object());
        if (!jobStatuses.containsKey(job) || jobStatuses.get(job).isDone()) {
            synchronized (jobSyncObjects.get(job)) {
                if (!jobStatuses.containsKey(job) || jobStatuses.get(job).isDone()) {
                    logger.info("Starting job {}", job);
                    Future<?> future = executorService.submit(job);
                    jobStatuses.put(job, future);
                } else {
                    logger.info("Job {} is already running, skipping this time...", job);
                }
            }
        } else {
            logger.info("Job {} is already running, skipping this time...", job);
        }
    }
}
