package com.dbs.replsdk.model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeviceTest {

    Device item1;
    Device item2;
    Device item3;
    String deviceType = "device_type";
    String deviceOS = "device_os";
    String deviceModel = "device_model";
    String deviceId = "device_id";

    @Before
    public void setUp() {
        item1 = new Device();
        item1.setType(deviceType);
        item1.setModel(deviceModel);
        item1.setOs(deviceOS);
        item1.setId(deviceId);
    }

    @Test
    public void shouldGetType() {
        assertEquals(deviceType, item1.getType());
    }

    @Test
    public void shouldGetModel() {
        assertEquals(deviceModel, item1.getModel());
    }

    @Test
    public void shouldGetOs() {
        assertEquals(deviceOS, item1.getOs());
    }

    @Test
    public void shouldGetId() {
        assertEquals(deviceId, item1.getId());
    }
}
