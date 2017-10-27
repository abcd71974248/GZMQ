package com.hotsun.mqxxgl.gis.presenter;

import android.content.Context;

import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.service.LiveNetworkMonitor;
import com.hotsun.mqxxgl.gis.service.NetworkMonitor;
import com.hotsun.mqxxgl.gis.util.ResourcesUtil;

/**
 * Created by li on 2017/10/26.
 * 图层类
 */

public class LayerPresenter {

    /**
     * 加载地图
     */
    public void addBaseLayer(Context context,MapView mapView){
        Basemap basemap = new Basemap(getTitleLayer(context));
        ArcGISMap map = new ArcGISMap(basemap);
        mapView.setMap(map);
        BackgroundGrid backgroundGrid = new BackgroundGrid(0xffffff, 0xffffff, 3, 3);
        mapView.setBackgroundGrid(backgroundGrid);

    }

    /**获取离线地图*/
    private ArcGISTiledLayer getTitleLayer(Context context){
        ArcGISTiledLayer tiledLayer;
        NetworkMonitor networkMonitor = new LiveNetworkMonitor(context);
        if(networkMonitor.isConnected()){
            String serverUrl = context.getResources().getString(R.string.gzserver);
            tiledLayer = new ArcGISTiledLayer(serverUrl);
        }else{
            ResourcesUtil resourcesUtil = new ResourcesUtil(context);
            String titlepath = resourcesUtil.getBaseTitlePath();
            TileCache tileCache = new TileCache(titlepath);
            tiledLayer = new ArcGISTiledLayer(tileCache);
        }
        return tiledLayer;
    }

}
