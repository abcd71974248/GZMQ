package com.hotsun.mqxxgl.busi.model.requestParams;

/**
 * Created by yuan on 2017-10-27.
 */

public class UserLoginVo {
    String userNumber;
    String password;

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    String UUID;



}
