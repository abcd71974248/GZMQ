package com.hotsun.mqxxgl.gis.model;

import android.graphics.Color;
import android.location.*;
import android.os.Bundle;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;
import com.hotsun.mqxxgl.gis.view.ILocationView;

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
            gpspoint = new Point(location.getLongitude(),location.getLatitude());

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
        if(gpspoint != null){
           return (Point) GeometryEngine.project(gpspoint,MySpatialReference.getGpsSpatialReference(),MySpatialReference.getMapSpatialReference());
        }
        return new Point();
    }
}
