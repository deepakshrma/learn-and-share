package com.dbs.replsdk.model;

public class Platform {

    private String name;
    private String conversation_id;
    private String session_id;
    private String version;

    public Platform() {
        conversation_id = "prelogin";
        name = "MB-PRELOGIN-POSB";
        session_id = "";
        version = "1.2";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
