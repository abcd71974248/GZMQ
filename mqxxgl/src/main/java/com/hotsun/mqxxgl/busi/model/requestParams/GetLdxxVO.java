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







    public LdConditionText getConditionText() {
        return conditionText;
    }

    public void setConditionText(LdConditionText conditionText) {
        this.conditionText = conditionText;
    }

    LdConditionText conditionText;




}
