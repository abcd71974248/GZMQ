package com.hotsun.mqxxgl.gis.presenter;

import android.content.Context;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.esri.android.map.LocationDisplayManager;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.hotsun.mqxxgl.gis.model.GisLocationListener;
import com.hotsun.mqxxgl.gis.model.LocationListener;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;

/**
 * Created by li on 2017/10/26.
 * 定位类
 */

public class LocationPresenter{

    private GisLocationListener myLocationListener;
    private IGisBaseView baseView;

    public LocationPresenter(IGisBaseView baseView){
        this.baseView = baseView;
    }

    public void initArcgisLocation(IGisBaseView baseView){
        final LocationDisplayManager locationDisplayManager = baseView.getMapView().getLocationDisplayManager();
        locationDisplayManager.setShowLocation(true);
        locationDisplayManager.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION );//设置模式
        locationDisplayManager.setShowPings(true);
        locationDisplayManager.setAllowNetworkLocation(true);
        locationDisplayManager.start();//开始定位
        myLocationListener = new GisLocationListener(baseView);
        locationDisplayManager.setLocationListener(myLocationListener);

        baseView.getMapView().setExtent(locationDisplayManager.getPoint());
        baseView.getMapView().setScale(2000);
        baseView.getMapView().setResolution(0.53);
    }

    /**
     *定位到当前位置
     */
    public void zoomTolocation(){
        Geometry geometry = getCurPoint();
        baseView.getMapView().setExtent(geometry);
        baseView.getMapView().setScale(2000);
        baseView.getMapView().setResolution(0.53);
    }
    /**返回当前点位信息*/
    public Point getCurPoint(){
        return myLocationListener.getCurPoint();
    }

    /**返回当前点位信息*/
    public Point getCurGpsPoint(){
        return myLocationListener.getCurGpsPoint();
    }

    /**
     * 初始化百度定位设置
     */
    public LocationClient initLocation(Context context) {
        LocationClient client = new LocationClient(context);
        LocationListener myLocationListener = new LocationListener(baseView);
        client.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型bd09ll gcj02 GCJ-02
        option.setScanSpan(1000);
        client.setLocOption(option);
        client.start();
        return client;
    }

}
