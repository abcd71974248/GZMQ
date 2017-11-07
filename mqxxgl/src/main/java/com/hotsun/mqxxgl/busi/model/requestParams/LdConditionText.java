package com.hotsun.mqxxgl.busi.model.requestParams;

/**
 * Created by yuan on 2017-10-27.
 */

public class LdConditionText {
    String cunid;
    String zuid;
    String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCunid() {
        return cunid;
    }

    public void setCunid(String cunid) {
        this.cunid = cunid;
    }

    public String getZuid() {
        return zuid;
    }

    public void setZuid(String zuid) {
        this.zuid = zuid;
    }

    public String getLdmc() {
        return ldmc;
    }

    public void setLdmc(String ldmc) {
        this.ldmc = ldmc;
    }

    String ldmc;

}
