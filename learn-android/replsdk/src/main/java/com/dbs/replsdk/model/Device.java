package com.dbs.replsdk.model;

public class Device {
    private String type;
    private String os;
    private String model;
    private String id;

    public Device() {
        type = "Mobile";
        model = "";
        id = "{1EAF-2DAB-1EFA-258D}";
        os = "Android";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
