package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DefaultJobRunStrategyTest {
    private JobRunStrategy sut;

    @Before
    public void setUp() throws Exception {
        sut = new DefaultJobRunStrategy();
    }

    @Test(timeout = 100)
    public void executeAllJobsSimultaneously() throws Exception{
        // given
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable job = mock(Runnable.class);

        // when
        for (int i=0;i<10;i++) {
            sut.run(executorService, job);
        }

        // then
        executorService.shutdownNow();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        verify(job, times(10)).run();
    }


    @Test
    public void doNothingIfExecutorServiceIsNull() throws Exception {
        // given
        Runnable job = mock(Runnable.class);

        // when
        sut.run(null, job);

        // then
        verifyZeroInteractions(job);
    }

    @Test
    public void doNothingIfJobIsNull() throws Exception {
        // given
        ExecutorService executorService = mock(ExecutorService.class);

        // when
        sut.run(executorService, null);

        // then
        verifyZeroInteractions(executorService);
    }
}
