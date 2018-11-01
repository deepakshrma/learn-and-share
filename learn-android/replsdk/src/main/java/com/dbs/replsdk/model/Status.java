package com.dbs.replsdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("code")
    @Expose
    private String code;

    /**
     * No args constructor for use in serialization
     *
     */
    public Status() {
    }

    /**
     *
     * @param code
     */
    public Status(String code) {
        super();
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}