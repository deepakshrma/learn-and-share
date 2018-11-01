package com.dbs.replsdk.model;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    String session_id;
    String locale;
    String time_zone;
    JSONObject meta_fields;
    User user;

    @Before
    public void setUp() {
        locale = "en_US";
        session_id = "";
        time_zone = "GMT+08:00";
        meta_fields = new JSONObject();

        user = new User();
        user.setMeta_fields(meta_fields);
        user.setTime_zone(time_zone);
        user.setSession_id(session_id);
        user.setLocale(locale);
    }


    @Test
    public void shouldGetLocale() {
        assertEquals(locale, user.getLocale());
    }

    @Test
    public void shouldGetSession() {
        assertEquals(session_id, user.getSession_id());
    }

    @Test
    public void shouldGetTimeZone() {
        assertEquals(time_zone, user.getTime_zone());
    }

    @Test
    public void shouldGetMetaFields() {
        assertEquals(meta_fields, user.getMeta_fields());
    }
}
