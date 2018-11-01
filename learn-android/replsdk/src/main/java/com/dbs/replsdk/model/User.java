package com.dbs.replsdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class User {
    @SerializedName("session_id")
    @Expose
    private String session_id;
    private String locale;
    private String time_zone;
    private JSONObject meta_fields;

    public User() {
        locale = "en_US";
        session_id = "";
        time_zone = "GMT+08:00";
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public JSONObject getMeta_fields() {
        return meta_fields;
    }

    public void setMeta_fields(JSONObject meta_fields) {
        this.meta_fields = meta_fields;
    }

}
