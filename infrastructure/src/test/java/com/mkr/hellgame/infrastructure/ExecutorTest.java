package com.mkr.hellgame.infrastructure;

import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;

public class ExecutorTest {
    @Test(expected = NullPointerException.class)
    public void start_IfConfigurationIsNull_ThrowNullPointerException() throws Exception {
        // given
        Executor sut = new Executor(null);

        // when
        sut.start();

        // then
    }

    @Test(expected = NullPointerException.class)
    public void start_IfExecutorServiceIsNull_ThrowNullPointerException() throws Exception {
        // given
        Configuration configuration = new Configuration();
        Executor sut = new Executor(configuration);

        // when
        sut.start();

        // then
    }

    @Test
    public void start_IfAllClear_RootTimerServiceShouldBeStarted() throws Exception {
        // given
        ExecutorService executorService = mock(ExecutorService.class);
        Configuration configuration = new Configuration();
        configuration.setExecutorService(executorService);
        Executor sut = new Executor(configuration);

        // when
        sut.start();

        // then
        verify(executorService).submit(any(RootTimerService.class));
    }
}
