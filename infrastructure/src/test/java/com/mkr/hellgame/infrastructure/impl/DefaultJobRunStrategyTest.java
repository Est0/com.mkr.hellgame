package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

    @Test
    public void run_IfAllIsOk_ThenExecuteAllJobsSimultaneously() throws Exception{
        // given
        ExecutorService executorService = mock(ExecutorService.class);
        Runnable job = mock(Runnable.class);

        // when
        for (int i=0;i<10;i++) {
            sut.run(executorService, job);
        }

        // then
        verify(executorService, times(10)).submit(any(Runnable.class));
    }


    @Test
    public void run_IfExecutorServiceIsNull_ThenDoNothing() throws Exception {
        // given
        Runnable job = mock(Runnable.class);

        // when
        sut.run(null, job);

        // then
        verifyZeroInteractions(job);
    }

    @Test
    public void run_IfJobIsNull_ThenDoNothing() throws Exception {
        // given
        ExecutorService executorService = mock(ExecutorService.class);

        // when
        sut.run(executorService, null);

        // then
        verifyZeroInteractions(executorService);
    }
}
