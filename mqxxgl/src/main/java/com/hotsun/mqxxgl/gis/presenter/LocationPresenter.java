package com.hotsun.mqxxgl.gis.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.esri.android.map.LocationDisplayManager;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.hotsun.mqxxgl.gis.model.GisLocationListener;
import com.hotsun.mqxxgl.gis.model.LocationListener;
import com.hotsun.mqxxgl.gis.model.MySpatialReference;
import com.hotsun.mqxxgl.gis.util.ConverterUtil;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;
import com.hotsun.mqxxgl.gis.view.ILocationView;

/**
 * Created by li on 2017/10/26.
 * 定位类
 */

public class LocationPresenter implements BDLocationListener,ILocationView{

    private IGisBaseView baseView;
    private LocationDisplayManager locationDisplay;
    private boolean isFirst = true;
    private Point gpspoint;
    private Point mapPoint;

    public LocationPresenter(IGisBaseView baseView){
        this.baseView = baseView;
        locationDisplay = baseView.getMapView().getLocationDisplayManager();
        initLocation(baseView.getContext());
    }

    public void initArcgisLocation(IGisBaseView baseView){
        if(locationDisplay == null){
            locationDisplay = baseView.getMapView().getLocationDisplayManager();
        }
        locationDisplay.setShowLocation(true);
        locationDisplay.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION );//设置模式
        locationDisplay.setShowPings(true);
        locationDisplay.setAllowNetworkLocation(true);
        locationDisplay.start();//开始定位

    }

    /**
     *定位到当前位置
     */
    public void zoomTolocation(){
        Geometry geometry = getCurPoint();
        baseView.getMapView().setExtent(geometry);
        baseView.getMapView().setScale(3000);
        baseView.getMapView().setResolution(0.55);
    }
    /**返回当前点位信息*/
    public Point getCurPoint(){
        return mapPoint;
    }

    /**返回当前点位信息*/
    public Point getCurGpsPoint(){
        return gpspoint;
    }

    /**
     * 初始化百度定位设置
     */
    public LocationClient initLocation(Context context) {
        LocationClient client = new LocationClient(context);
        client.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型bd09ll gcj02 GCJ-02
        option.setScanSpan(1000);
        client.setLocOption(option);
        client.start();
        return client;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Point point = locationDisplay.getPoint();
        if(point != null && point.isValid()){
            mapPoint = point;
            gpspoint = new Point(locationDisplay.getLocation().getLongitude(),locationDisplay.getLocation().getLatitude());
            Point point1 =(Point) GeometryEngine.project(mapPoint,MySpatialReference.getMapSpatialReference(),MySpatialReference.getGpsSpatialReference());
        }else{
            gpspoint = getGPSpoint(bdLocation);
            mapPoint = (Point) GeometryEngine.project(gpspoint,MySpatialReference.getGpsSpatialReference(),MySpatialReference.getMapSpatialReference());
        }
        Log.e("======",mapPoint.getX()+"+++++++"+mapPoint.getY());

        Log.e("++++++",gpspoint.getX()+"+++++++"+gpspoint.getY());

        if(isFirst){
            baseView.getMapView().setExtent(mapPoint);
            baseView.getMapView().setScale(3000);
            baseView.getMapView().setResolution(0.55);
            isFirst = false;
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

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
        /* 判别是取的gps坐标还是百度坐标 true 为百度坐标 */
        double lon = 0.0,lat = 0.0,altitude = 0.0;// 经度
        if (location != null) {
            //GPS坐标
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
        }

        return new Point(lon,lat);
    }
}
