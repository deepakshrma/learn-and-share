package com.dbs.replsdk.arch;

import com.dbs.replsdk.model.Device;
import com.dbs.replsdk.model.Platform;
import com.dbs.replsdk.model.RequestContext;
import com.dbs.replsdk.model.User;

/**
 * Session Manager
 */
public class SessionManager {

    private static volatile SessionManager INSTANCE;
    private Device device;
    private Platform platform;
    private User user;
    private String apiVersion;

    public static SessionManager getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (SessionManager.class) {
            if (INSTANCE == null) {
                INSTANCE = new SessionManager();
                INSTANCE.device = new Device();
                INSTANCE.platform = new Platform();
                INSTANCE.user = new User();
                INSTANCE.apiVersion = "2.1.2";
            }
        }
        return INSTANCE;
    }

    /**
     * Set device info for the request
     *
     * @param device
     */
    public void setDevice(Device device) {
        this.device = device;
    }

    /**
     * Set the platform
     *
     * @param platform
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    /**
     * Set user
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set api version
     *
     * @param apiVersion
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * Generate the request context;
     *
     * @return
     */
    public RequestContext createContext() {

        RequestContext requestContext = new RequestContext();

        requestContext.setApi_version(apiVersion);

        if (device == null) {
            throw new IllegalStateException("Device is null");
        }
        requestContext.setDevice(device);
        if (platform == null) {
            throw new IllegalStateException("Platform is null");
        }
        requestContext.setPlatform(platform);
        if (user == null) {
            throw new IllegalStateException("User is null");
        }
        requestContext.setUser(user);

        return requestContext;
    }
}