package com.dbs.replsdk.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseContext {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("api_version")
    @Expose
    private String apiVersion;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseContext() {
    }

    /**
     *
     * @param apiVersion
     * @param user
     */
    public ResponseContext(User user, String apiVersion) {
        super();
        this.user = user;
        this.apiVersion = apiVersion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

}
