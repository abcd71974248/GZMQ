package com.hotsun.mqxxgl.gis.presenter;

import android.content.Context;

import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.hotsun.mqxxgl.R;

/**
 * Created by li on 2017/10/26.
 */

public class LayerPresenter {

    /**
     * 加载地图
     */
    public void addBaseLayer(Context context,MapView mapView){

        String serverUrl = context.getResources().getString(R.string.gzserver);
        ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(serverUrl);
        Basemap basemap = new Basemap(tiledLayer);
        ArcGISMap map = new ArcGISMap(basemap);
        mapView.setMap(map);

        BackgroundGrid backgroundGrid = new BackgroundGrid(0xffffff, 0xffffff, 3, 3);
        mapView.setBackgroundGrid(backgroundGrid);

    }


}
