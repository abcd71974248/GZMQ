package com.hotsun.mqxxgl.gis.model;

import android.graphics.Color;
import android.location.*;
import android.os.Bundle;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.hotsun.mqxxgl.busi.model.DeviceUuidFactory;
import com.hotsun.mqxxgl.gis.util.SqliteUtil;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;
import com.hotsun.mqxxgl.gis.view.ILocationView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by li on 2017/11/7.
 * GIS LocationListener
 */

public class GisLocationListener implements android.location.LocationListener,ILocationView {

    private IGisBaseView baseView;
    private Point gpspoint;

    public GisLocationListener(IGisBaseView baseView){
        this.baseView = baseView;
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            this.gpspoint = new Point(location.getLongitude(),location.getLatitude());
            if(gpspoint != null){
                DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(baseView.getContext());
                String uuid = deviceUuidFactory.getDeviceUuid().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf.format(new Date());
                String lon = location.getLongitude()+"";
                String lat = location.getLatitude()+"";
                Gjpoint gjpoint = new Gjpoint(lon,lat,uuid,time,"0");
                SqliteUtil.addLocalPoint(baseView.getContext(),gjpoint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public Point getCurGpsPoint() {
        return gpspoint;
    }

    @Override
    public Point getCurPoint() {
        if(this.gpspoint != null){
           return (Point) GeometryEngine.project(gpspoint,MySpatialReference.getGpsSpatialReference(),MySpatialReference.getMapSpatialReference());
        }
        return new Point();
    }
}
