package com.hotsun.mqxxgl.busi.model.requestParams;

/**
 * Created by yuan on 2017-10-27.
 */

public class GetLdxxVO {


    int start;
    int limit;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    String userID;

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

    String sessionID;







   String zuid;
    String ldmc;

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
}
