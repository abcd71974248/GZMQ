package com.hotsun.mqxxgl.gis.presenter;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.hotsun.mqxxgl.gis.model.LocationListener;

/**
 * Created by li on 2017/10/26.
 * 定位类
 */

public class LocationPresenter {

    private MapView mapView;
    private LocationListener myLocationListener;

    public LocationPresenter(MapView mapView){
        this.mapView = mapView;
        myLocationListener = new LocationListener(mapView);
    }

    /**
     * 初始化定位设置
     */
    public void initLocation(MapView mapView) {
        LocationDisplay display = mapView.getLocationDisplay();
        display.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
        display.startAsync();
        myLocationListener = new LocationListener(mapView);
        display.addLocationChangedListener(myLocationListener);
    }

    /**
     *定位到当前位置
     */
    public void zoomTolocation(){
        Point point = myLocationListener.getCurPoint();
        if(point != null && !point.isEmpty()){
            mapView.setViewpointCenterAsync(point,1.5);
        }
    }
    /**返回当前点位信息*/
    public Point getCurPoint(){
        return myLocationListener.getCurPoint();
    }

}
