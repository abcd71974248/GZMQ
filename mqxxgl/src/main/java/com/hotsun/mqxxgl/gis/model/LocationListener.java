package com.hotsun.mqxxgl.gis.model;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.location.LocationDataSource;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.hotsun.mqxxgl.gis.view.ILocationView;

/**
 * 地图定位获取坐标实现类
 * Created by li on 2017/10/24.
 */

public class LocationListener implements LocationDisplay.LocationChangedListener {

    private Point gpspoint;
    private MapView mapView;
    private boolean isFirstLocation = false;

    public LocationListener(MapView mapView){
        this.mapView = mapView;
    }

    @Override
    public void onLocationChanged(LocationDisplay.LocationChangedEvent locationChangedEvent) {
        LocationDataSource.Location location = locationChangedEvent.getLocation();
        gpspoint = location.getPosition();
        if(!isFirstLocation && gpspoint != null && !gpspoint.isEmpty()){
            mapView.setViewpointCenterAsync(gpspoint,3000);
            isFirstLocation = true;
        }else{
            if(gpspoint == null){
                gpspoint = new Point(0,0);
                return;
            }
            Viewpoint viewpoint = new Viewpoint(gpspoint,3000,1.0);
            mapView.getMap().setInitialViewpoint(viewpoint);
        }
    }

}
