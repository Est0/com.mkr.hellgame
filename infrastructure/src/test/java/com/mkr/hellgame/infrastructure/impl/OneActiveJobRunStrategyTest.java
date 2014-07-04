package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class OneActiveJobRunStrategyTest {
    private JobRunStrategy sut;

    @Before
    public void setUp() throws Exception {
        sut = new OneActiveJobRunStrategy();
    }

    @Test
    public void run_ExecuteOnlyOneJobOfTheSameTypeAtATime() throws Exception{
        // given
        Future future = mock(Future.class);
        when(future.get()).thenReturn(false);
        ExecutorService executorService = mock(ExecutorService.class);
        when(executorService.submit(any(Runnable.class))).thenReturn(future);
        Runnable job = mock(Runnable.class);

        // when
        for (int i=0;i<10;i++) {
            sut.run(executorService, job);
        }

        // then
        verify(executorService).submit(any(Runnable.class));
    }


    @Test
    public void run_DoNothingIfExecutorServiceIsNull() throws Exception {
        // given
        Runnable job = mock(Runnable.class);

        // when
        sut.run(null, job);

        // then
        verifyZeroInteractions(job);
    }

    @Test
    public void run_DoNothingIfJobIsNull() throws Exception {
        // given
        ExecutorService executorService = mock(ExecutorService.class);

        // when
        sut.run(executorService, null);

        // then
        verifyZeroInteractions(executorService);
    }
}
