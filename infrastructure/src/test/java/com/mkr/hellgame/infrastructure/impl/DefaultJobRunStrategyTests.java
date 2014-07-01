package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.Job;
import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DefaultJobRunStrategyTests {
    @Test
    public void executeJobsOfTheSameTypeSimultaneously() {
        class IntWrapper {
            public int value;
        }
        final IntWrapper counter = new IntWrapper();
        Job job = new Job() {
                    @Override
                    public void run() {
                        try {
                            counter.value++;
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                        }
                    }
                };
        ExecutorService executorService = Executors.newCachedThreadPool();
        JobRunStrategy jobRunStrategy = new DefaultJobRunStrategy();
        for (int i = 0; i < 10; i++) {
            jobRunStrategy.run(executorService, job);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
        Assert.assertEquals(10, counter.value);
    }
}
