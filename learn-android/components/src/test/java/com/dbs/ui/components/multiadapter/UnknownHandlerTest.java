package com.dbs.ui.components.multiadapter;

import com.dbs.ui.multiadapter.UnknownEventHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class UnknownHandlerTest {

    @Test
    public void shouldGetCorrect() {
        UnknownEventHandler handler = new UnknownEventHandler();
        assertEquals(Integer.MIN_VALUE, handler.getKey());
    }
}
