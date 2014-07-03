package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class OneActiveJobRunStrategyTest {
    private JobRunStrategy sut;

    @Before
    public void setUp() throws Exception {
        sut = new OneActiveJobRunStrategy();
    }

    @Test(timeout = 100)
    public void executeOnlyOneJobOfTheSameTypeAtATime() throws Exception{
        // given
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable job = mock(Runnable.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Thread.sleep(Long.MAX_VALUE);
                return null;
            }
        }).when(job).run();


        // when
        for (int i=0;i<10;i++) {
            sut.run(executorService, job);
        }

        // then
        executorService.shutdownNow();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        verify(job, times(1)).run();
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
