package com.hotsun.mqxxgl.busi.model.requestParams;

/**
 * Created by yuan on 2017-10-27.
 */

public class GetSingleDataVO {


    String userID;
    String sessionID;
    String dataid;

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

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }
}
