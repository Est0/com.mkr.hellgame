package com.mkr.hellgame.infrastructure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;

public class ExecutorTest {
    private Executor sut;
    private ExecutorService executorService;
    private Configuration configuration;

    @Before
    public void setUp() throws Exception {
        executorService = mock(ExecutorService.class);
        configuration = new Configuration();
        configuration.setExecutorService(executorService);
        sut = new Executor(configuration);
    }

    @Test(expected = NullPointerException.class)
    public void start_IfConfigurationIsNull_ThenThrowNullPointerException() throws Exception {
        // given
        Executor sut = new Executor(null);

        // when
        sut.start();

        // then
    }

    @Test(expected = NullPointerException.class)
    public void start_IfExecutorServiceIsNull_ThenThrowNullPointerException() throws Exception {
        // given
        Configuration configuration = new Configuration();
        Executor sut = new Executor(configuration);

        // when
        sut.start();

        // then
    }

    @Test
    public void start_IfFirstTimeInvocation_ThenStartRootTimerService() throws Exception {
        // given

        // when
        sut.start();

        // then
        verify(executorService).submit(any(RootTimerService.class));
    }

    @Test(expected = ExecutorAlreadyStartedException.class)
    public void start_IfSecondAndSubsequentInvocation_ThenThrowExecutorAlreadyStartedException() throws Exception {
        // given
        sut.start();

        // when
        sut.start();

        // then
    }

    @Test(expected = ExecutorAlreadyStartedException.class)
    public void start_IfInvokedAfterStop_ThenThrowExecutorAlreadyStartedException() throws Exception {
        // given
        when(executorService.isShutdown()).thenReturn(false).thenReturn(true);
        sut.start();
        sut.stop(0, TimeUnit.SECONDS);

        // when
        sut.start();

        // then
    }

    @Test
    public void stop_IfInvokedCorrectly_ThenStopInTime() throws Exception {
        // given
        when(executorService.awaitTermination(leq(1L), eq(TimeUnit.MILLISECONDS))).thenReturn(true);
        when(executorService.awaitTermination(gt(1L), eq(TimeUnit.MILLISECONDS))).thenReturn(false);
        sut.start();

        // when
        boolean result = sut.stop(1, TimeUnit.MILLISECONDS);

        // then
        Assert.assertEquals(true, result);
    }

    @Test(expected = ExecutorNotStartedYetException.class)
    public void stop_IfInvokedWithoutStart_ThenThrowExecutorNotStartedYetException() throws Exception {
        // given

        // when
        sut.stop(0, TimeUnit.SECONDS);

        // then
    }

    @Test(expected = ExecutorAlreadyStoppedException.class)
    public void stop_IfSecondAndSubsequentInvocation_ThenThrowExecutorAlreadyStoppedException() throws Exception {
        // given
        when(executorService.isShutdown()).thenReturn(false).thenReturn(true);
        sut.start();
        sut.stop(0, TimeUnit.SECONDS);

        // when
        sut.stop(0, TimeUnit.SECONDS);

        // then
    }

    @Test
    public void stop_IfWasInterrupted_ThenReturnFalse() throws Exception {
        // given
        when(executorService.awaitTermination(any(Integer.class), any(TimeUnit.class))).thenThrow(new InterruptedException());
        sut.start();

        // when
        boolean result = sut.stop(0, TimeUnit.SECONDS);

        // then
        Assert.assertEquals(false, result);
    }
}
