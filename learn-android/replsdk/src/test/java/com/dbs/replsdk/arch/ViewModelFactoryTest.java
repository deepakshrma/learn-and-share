package com.dbs.replsdk.arch;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;

public class ViewModelFactoryTest {

    @Mock
    Worker worker;

    @Mock
    Context mMockedContext;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void initVM_shouldReturnNonNull() {
        ViewModelFactory factory = new ViewModelFactory(worker);
        ChatBotViewModel vm = factory.create(ChatBotViewModel.class);
        assertNotNull(vm);
    }

    @Test(expected = IllegalArgumentException.class)
    public void initDUMP_shouldThrowException() {
        ViewModelFactory factory = new ViewModelFactory(worker);
        DUMB_VM vm = factory.create(DUMB_VM.class);
    }


    private static class DUMB_VM extends ViewModel {
    }

}
