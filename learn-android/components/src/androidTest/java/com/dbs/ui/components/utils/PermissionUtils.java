package com.dbs.ui.components.utils;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class PermissionUtils {

    private static final String REVOKE_PERMISSION_COMMAND = "adb shell pm revoke %s ";

    public static void resetPermissions(String permission) {
        String command = "adb shell pm revoke + " + permission;
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void denyRuntimePermission() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject allowPermissions = device.findObject(new UiSelector().clickable(true).checkable(false).index(0));
        if (allowPermissions.exists()) {
            allowPermissions.click();
        }
    }

    public static void selectDoNotAskMeAgain() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject allowPermissions = device.findObject(new UiSelector().clickable(true).checkable(true));
        if (allowPermissions.exists()) {
            allowPermissions.click();

        }
    }

    public static void allowPermission() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject allowPermissions = device.findObject(new UiSelector().clickable(true).checkable(false).index(1));
        if (allowPermissions.exists()) {
            allowPermissions.click();
        }
    }

    public static void openPermissionsScreenInSettings() throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject allowPermissions = device.findObject(new UiSelector().checkable(false).text("Permissions"));
        if (allowPermissions.exists()) {
            allowPermissions.click();
        }
    }

    public static void enablePermissionInSettings(String permission) throws UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject allowPermissions = device.findObject(new UiSelector().checkable(false).text(permission));
        if (allowPermissions.exists()) {
            allowPermissions.click();
        }
    }
}

