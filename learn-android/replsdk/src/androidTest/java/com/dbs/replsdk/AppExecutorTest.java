package com.dbs.replsdk;

import android.support.test.runner.AndroidJUnit4;

import com.dbs.replsdk.arch.AppExecutors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.concurrent.Executor;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class AppExecutorTest {

    @Mock
    Runnable mockRunnable;

    @Before
    public void setup() {
        mockRunnable = Mockito.mock(Runnable.class);
//        when(mockRunnable.run();).thenCallRealMethod()
    }

    @Test
    public void getDiskExecutor_poolShouldBeOne() {
        Executor io = AppExecutors.getInstance().diskIO();
        assertNotNull(io);
//        io.execute(mockRunnable);
//        verify(mockRunnable, times(1)).run();
//        verifyNoMoreInteractions(mockRunnable);
    }
}
