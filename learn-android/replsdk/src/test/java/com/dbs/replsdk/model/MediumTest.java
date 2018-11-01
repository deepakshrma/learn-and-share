package com.dbs.replsdk.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MediumTest {

    Medium item1;
    String medium_url = "abdc";
    String medium_hyperlink = "mnop";

    @Before
    public void setUp() {
        item1 = new Medium();
        item1.setHyperlink_url(medium_hyperlink);
        item1.setMedium_url(medium_url);
    }

    @Test
    public void shouldGetHyperlink() {
        assertEquals(medium_hyperlink, item1.getHyperlink_url());
    }

    @Test
    public void shouldGetUrl() {
        assertEquals(medium_url, item1.getMedium_url());
    }

}
