package com.hotsun.mqxxgl.gis.model;

import java.io.Serializable;

/**
 * Created by li on 2017/11/8.
 * 记录轨迹点
 */

public class Gjpoint implements Serializable{

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getSbh() {
        return sbh;
    }

    public void setSbh(String sbh) {
        this.sbh = sbh;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String lon;
    private String lat;
    private String sbh;
    private String time;
    private String state;

    public Gjpoint(){

    }

    public Gjpoint(String lon,String lat,String sbh,String time,String state){
        this.lon = lon;
        this.lat = lat;
        this.sbh = sbh;
        this.time = time;
        this.state = state;
    }

}
