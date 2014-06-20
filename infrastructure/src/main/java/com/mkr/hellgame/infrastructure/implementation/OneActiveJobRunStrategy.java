package com.mkr.hellgame.infrastructure.implementation;

import com.mkr.hellgame.infrastructure.abstraction.Job;
import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;

import java.util.Map;
import java.util.concurrent.*;

public class OneActiveJobRunStrategy implements JobRunStrategy {
    private Map<Job, Future<Boolean>> jobStatuses = new ConcurrentHashMap<>();
    private ConcurrentMap<Job, Object> jobSyncObjects = new ConcurrentHashMap<>();

    @Override
    public void run(ExecutorService executorService, final Job job) {
        jobSyncObjects.putIfAbsent(job, new Object());
        if (!jobStatuses.containsKey(job) ||
                jobStatuses.get(job).isDone()) {
            synchronized (jobSyncObjects.get(job)) {
                if (!jobStatuses.containsKey(job) ||
                        jobStatuses.get(job).isDone()) {
                    Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            try {
                                job.run();
                            }
                            catch (Exception e) {
                                return false;
                            }
                            return true;
                        }
                    });
                    jobStatuses.put(job, future);
                }
            }
        }
    }
}
