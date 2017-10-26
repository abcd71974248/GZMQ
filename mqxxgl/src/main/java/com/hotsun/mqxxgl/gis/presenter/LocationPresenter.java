package com.hotsun.mqxxgl.gis.presenter;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.hotsun.mqxxgl.gis.model.MyLocationListener;

/**
 * Created by li on 2017/10/26.
 */

public class LocationPresenter {

    MapView mapView;
    MyLocationListener myLocationListener;

    public LocationPresenter(MapView mapView){
        this.mapView = mapView;
        myLocationListener = new MyLocationListener(mapView);
    }

    /**
     * 初始化定位设置
     */
    public void initLocation(MapView mapView) {
        LocationDisplay display = mapView.getLocationDisplay();
        display.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
        display.startAsync();
        myLocationListener = new MyLocationListener(mapView);
        display.addLocationChangedListener(myLocationListener);
    }

    /**
     *定位到当前位置
     */
    public void zoomTolocation(){
        Point point = myLocationListener.getCurPoint();
        mapView.setViewpointCenterAsync(point,1.5);
    }

}
