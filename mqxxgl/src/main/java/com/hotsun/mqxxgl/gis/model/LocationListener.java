package com.hotsun.mqxxgl.gis.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.esri.android.map.LocationDisplayManager;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.MarkerSymbol;
import com.hotsun.mqxxgl.gis.util.ConverterUtil;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;
import com.hotsun.mqxxgl.gis.view.ILocationView;

/**
 * 地图定位获取坐标实现类
 * Created by li on 2017/10/24.
 */

public class LocationListener implements BDLocationListener, ILocationView {

    private Point gpspoint;
    private Point mapoint;
    private MapView mapView;
    private boolean isFirstLocation = false;
    private LocationDisplayManager locationDisplayManager;
    private IGisBaseView baseView;
    private int id_circle;
    private int id_point;

    public LocationListener(IGisBaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        gpspoint = getGPSpoint(bdLocation);
        updataLocation();
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    @Override
    public Point getCurGpsPoint() {
        return gpspoint;
    }

    @Override
    public Point getCurPoint() {
        return (Point) GeometryEngine.project(gpspoint, SpatialReference.create(4326), MySpatialReference.getMapSpatialReference());
    }

    /*更新位置*/
    public void updataLocation() {
        if(locationDisplayManager == null){
            locationDisplayManager = baseView.getMapView().getLocationDisplayManager();
        }
        try {
            Point point = getCurPoint();
            if(id_point == 0 && id_circle == 0){
                FillSymbol fillSymbol = locationDisplayManager.getAccuracySymbol();
                fillSymbol.setColor(Color.RED);
                MarkerSymbol markerSymbol = locationDisplayManager.getDefaultSymbol();
                Graphic graphic = new Graphic(point,markerSymbol);
                Geometry geometry =(Geometry) GeometryEngine.buffer(point, MySpatialReference.getMapSpatialReference(), 20,null);
                Graphic graphic1 = new Graphic(geometry,fillSymbol);
                id_point = baseView.getGraphicsLayer().addGraphic(graphic);
                id_circle = baseView.getGraphicsLayer().addGraphic(graphic1);
            }else{
                baseView.getGraphicsLayer().updateGraphic(id_point,point);
                Geometry geometry =(Geometry) GeometryEngine.buffer(point, MySpatialReference.getMapSpatialReference(), 20,null);
                baseView.getGraphicsLayer().updateGraphic(id_circle,geometry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取位置坐标 GPS定位 网络定位 百度定位
     */
    private Point getGPSpoint(BDLocation blocation) {
        LocationManager locationManager = (LocationManager) baseView.getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(baseView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(baseView.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return new Point();
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if(location == null && blocation.getLongitude() == 4.9E-324 && blocation.getLatitude() == 4.9E-324){
            locationDisplayManager = mapView.getLocationDisplayManager();
            location = locationDisplayManager.getLocation();
        }
        /* 判别是取的gps坐标还是百度坐标 true 为百度坐标 */
        double lon = 0.0,lat = 0.0,altitude = 0.0;// 经度
        if (location != null) {
            //GPS坐标
            Log.e("百度GPS坐标",System.currentTimeMillis()+"======"+location.getLatitude()+"======"+location.getLongitude());
            lat = location.getLatitude(); // 纬度 26.567741305680546
            lon = location.getLongitude(); // 经度 106.68937683886078
            altitude = location.getAltitude();// 1040.8563754250627
        } else {
            //百度坐标
            lat = blocation.getLatitude(); // 纬度
            lon = blocation.getLongitude(); // 经度
            altitude = blocation.getAltitude();

            Point p1 = ConverterUtil.gps2gCoordinate(lon, lat);
            Point p2 = ConverterUtil.g2bCoordinate(p1.getX(), p1.getY());
            // wgs1984的坐标
            lon = 2 * lon - p2.getX();
            lat = 2 * lat - p2.getY();
            Log.e("百度",System.currentTimeMillis()+"======"+lon+"======"+lat);
        }

        return new Point(lon,lat);
    }

}
