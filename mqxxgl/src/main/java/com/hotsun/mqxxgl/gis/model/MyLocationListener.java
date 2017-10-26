package com.hotsun.mqxxgl.gis.model;

import android.content.Context;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.location.LocationDataSource;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.google.gson.internal.ConstructorConstructor;
import com.hotsun.mqxxgl.gis.view.ILocationView;

/**
 * 地图定位获取坐标实现类
 * Created by li on 2017/10/24.
 */

public class MyLocationListener implements LocationDisplay.LocationChangedListener,ILocationView {

    private Point point;
    private MapView mapView;
    private Context mContext;
    private boolean isFirstLocation = false;

    public MyLocationListener(MapView mapView){
        this.mapView = mapView;
    }

    @Override
    public void onLocationChanged(LocationDisplay.LocationChangedEvent locationChangedEvent) {
        LocationDataSource.Location location = locationChangedEvent.getLocation();
        point = location.getPosition();
        if(!isFirstLocation){
            mapView.setViewpointCenterAsync(point,1.0);
            isFirstLocation = true;
        }else{
            Viewpoint viewpoint = new Viewpoint(point,1.0,1.0);
            mapView.getMap().setInitialViewpoint(viewpoint);
        }
    }

    @Override
    public Point getCurPoint() {
        return point;
    }

}
