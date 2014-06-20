package com.mkr.hellgame.infrastructure.implementation;

import com.mkr.hellgame.infrastructure.abstraction.Job;
import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;

import java.util.concurrent.ExecutorService;

public class DefaultJobRunStrategy implements JobRunStrategy {
    @Override
    public void run(ExecutorService executorService, final Job job) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                job.run();
            }
        });
    }
}
