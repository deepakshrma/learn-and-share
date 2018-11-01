package com.dbs.replsdk.model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseContextTest {

    User user;
    String apiVersion;
    ResponseContext response, response2;

    @Before
    public void setUp() {
        response = new ResponseContext();
        user = new User();
        apiVersion = "apiVer";

        response.setApiVersion(apiVersion);
        response.setUser(user);

        response2 = new ResponseContext(user, apiVersion);

    }


    @Test
    public void shouldGetUser() {
        assertEquals(user, response.getUser());
    }

    @Test
    public void shouldGetApiVersion() {
        assertEquals(apiVersion, response.getApiVersion());
    }

    @Test
    public void shouldGetUserFromOtherConstructor() {
        assertEquals(user, response2.getUser());
    }

    @Test
    public void shouldGetApiVersionFromOtherConstructor() {
        assertEquals(apiVersion, response2.getApiVersion());
    }

}
