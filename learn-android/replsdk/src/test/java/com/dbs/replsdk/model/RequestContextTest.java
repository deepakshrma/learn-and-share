package com.dbs.replsdk.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestContextTest {

    RequestContext context;
    private Device device;
    private Platform platform;
    private User user;
    private String api_version;

    @Before
    public void setUp() {
        context = new RequestContext();
        device = new Device();
        platform = new Platform();
        user = new User();
        api_version = "apiVer";

        context.setDevice(device);
        context.setPlatform(platform);
        context.setUser(user);
        context.setApi_version(api_version);
    }


    @Test
    public void shouldGetDevice() {
        assertEquals(device, context.getDevice());
    }

    @Test
    public void shouldGetPlatform() {
        assertEquals(platform, context.getPlatform());
    }

    @Test
    public void shouldGetUser() {
        assertEquals(user, context.getUser());
    }

    @Test
    public void shouldGetApiVersion() {
        assertEquals(api_version, context.getApi_version());
    }
}
