package com.hotsun.mqxxgl.busi.model;

/**
 * Created by netbeans on 2017/11/8.
 */

public class AddLd {
    String userID;
    String sessionID;
    FwLdxx fwLdxx;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public FwLdxx getFwLdxx() {
        return fwLdxx;
    }

    public void setFwLdxx(FwLdxx fwLdxx) {
        this.fwLdxx = fwLdxx;
    }
}
