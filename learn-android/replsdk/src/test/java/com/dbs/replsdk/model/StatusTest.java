package com.dbs.replsdk.model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatusTest {

    Status status, status2;
    String code;

    @Before
    public void setUp() {
        status = new Status();
        code = "code";

        status.setCode(code);

        status2 = new Status(code);

    }


    @Test
    public void shouldGetCode() {
        assertEquals(code, status.getCode());
    }

    @Test
    public void shouldGetCodeFromOtherConstructor() {
        assertEquals(code, status2.getCode());
    }
}
