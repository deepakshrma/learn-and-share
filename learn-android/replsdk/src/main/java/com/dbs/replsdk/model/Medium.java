package com.dbs.replsdk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium {


    @SerializedName("medium_url")
    @Expose
    private String medium_url;
    @SerializedName("hyperlink_url")
    @Expose
    private String hyperlink_url;


    public String getMedium_url() {
        return medium_url;
    }

    public void setMedium_url(String medium_url) {
        this.medium_url = medium_url;
    }

    public String getHyperlink_url() {
        return hyperlink_url;
    }

    public void setHyperlink_url(String hyperlink_url) {
        this.hyperlink_url = hyperlink_url;
    }

}
