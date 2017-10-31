package com.hotsun.mqxxgl.gis.presenter;

import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.Symbol;
import com.hotsun.mqxxgl.gis.model.LocationListener;

/**
 * Created by li on 2017/10/26.
 * 定位类
 */

public class LocationPresenter {

    private MapView mapView;
    private LocationDisplay display;
    private LocationListener myLocationListener;

    public LocationPresenter(MapView mapView){
        this.mapView = mapView;
        myLocationListener = new LocationListener(mapView);
    }

    /**
     * 初始化定位设置
     */
    public void initLocation(MapView mapView) {
        display = mapView.getLocationDisplay();
        display.setAutoPanMode(LocationDisplay.AutoPanMode.COMPASS_NAVIGATION);
        display.startAsync();
        myLocationListener = new LocationListener(mapView);
        display.addLocationChangedListener(myLocationListener);
    }

    /**
     *定位到当前位置
     */
    public void zoomTolocation(){
        Point point = display.getLocation().getPosition();
        if(point != null && !point.isEmpty()){
            mapView.setViewpointCenterAsync(point,3000);
        }
    }
    /**返回当前点位信息*/
    public Point getCurPoint(){
        return display.getMapLocation();
    }

    /**返回当前点位信息*/
    public Point getCurGpsPoint(){
        return display.getLocation().getPosition();
    }

}
