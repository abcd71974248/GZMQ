package com.hotsun.mqxxgl.gis.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.model.MyLocationListener;

public class GisBaseActivity extends AppCompatActivity {

    MapView mapView;
    private LocationDisplay display;
    private MyLocationListener myLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gis_base);

        initView();

        addBaseLayer();

        initLocation();
    }

    /**
     * 初始化控件
     */
    private void initView(){
        mapView=(MapView) findViewById(R.id.mapview);
        mapView.setAttributionTextVisible(false);
    }

    /**
     * 加载地图
     */
    private void addBaseLayer(){

        String serverUrl = getResources().getString(R.string.gzserver);
        ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(serverUrl);
        Basemap basemap = new Basemap(tiledLayer);
        ArcGISMap map = new ArcGISMap(basemap);
        mapView.setMap(map);

        BackgroundGrid backgroundGrid = new BackgroundGrid(0xffffff, 0xffffff, 3, 3);
        mapView.setBackgroundGrid(backgroundGrid);

    }

    /**
     * 定位
     */
    private void initLocation(){
        display = mapView.getLocationDisplay();
        display.setAutoPanMode(LocationDisplay.AutoPanMode.NAVIGATION);
        display.startAsync();
        myLocationListener = new MyLocationListener(mapView);
        display.addLocationChangedListener(myLocationListener);
    }
}
