package com.dbs.replsdk.uimodel;

import com.google.gson.annotations.SerializedName;

public class Medium {
    @SerializedName("medium_url")
    private String mediumUrl;

    @SerializedName("hyperlink_url")
    private String hyperLinkUrl;

    public String getHyperLinkUrl() {
        return hyperLinkUrl;
    }

    public void setHyperLinkUrl(String hyperLinkUrl) {
        this.hyperLinkUrl = hyperLinkUrl;
    }


    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

}