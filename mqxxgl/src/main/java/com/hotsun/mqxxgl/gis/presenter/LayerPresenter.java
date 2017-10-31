package com.hotsun.mqxxgl.gis.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteTableLockedException;
import android.widget.BaseAdapter;

import com.esri.arcgisruntime.data.ArcGISFeature;
import com.esri.arcgisruntime.data.FeatureTemplate;
import com.esri.arcgisruntime.data.FeatureType;
import com.esri.arcgisruntime.data.Geodatabase;
import com.esri.arcgisruntime.data.GeodatabaseFeatureTable;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.util.ListenableList;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.model.LayerTemplate;
import com.hotsun.mqxxgl.gis.model.MyFeatureLayer;
import com.hotsun.mqxxgl.gis.model.MySpatialReference;
import com.hotsun.mqxxgl.gis.service.LiveNetworkMonitor;
import com.hotsun.mqxxgl.gis.service.NetworkMonitor;
import com.hotsun.mqxxgl.gis.util.ResourcesUtil;
import com.hotsun.mqxxgl.gis.util.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.Key;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by li on 2017/10/26.
 * 图层类
 */

public class LayerPresenter {

    private MapView mapView;
    private Context mContext;
    private GraphicsOverlay graphicsOverlay;
    public List<MyFeatureLayer> myFeatureLayers = new ArrayList<>();


    public LayerPresenter(Context context, MapView mapView) {
        this.mContext = context;
        this.mapView = mapView;
        graphicsOverlay = new GraphicsOverlay(GraphicsOverlay.RenderingMode.STATIC);
    }

    /**
     * 加载地图
     */
    public void addBaseLayer() {
        ArcGISTiledLayer tiledLayer = getTitleLayer(mContext);
        if (tiledLayer == null) {
            ToastUtil.setToast(mContext, "没有地图数据");
            return;
        }
        Basemap basemap = new Basemap(getTitleLayer(mContext));
        ArcGISMap map = new ArcGISMap(basemap);
        mapView.setMap(map);
        BackgroundGrid backgroundGrid = new BackgroundGrid(0xffffff, 0xffffff, 3, 3);
        mapView.setBackgroundGrid(backgroundGrid);
    }

    /**
     * 获取离线地图
     */
    private ArcGISTiledLayer getTitleLayer(Context context) {
        ArcGISTiledLayer tiledLayer;
        ResourcesUtil resourcesUtil = new ResourcesUtil(context);
        String titlepath = resourcesUtil.getBaseTitlePath();
        if (new File(titlepath).exists()) {
            //离线数据存在
            TileCache tileCache = new TileCache(titlepath);
            tiledLayer = new ArcGISTiledLayer(tileCache);
        } else {
            NetworkMonitor networkMonitor = new LiveNetworkMonitor(context);
            if (networkMonitor.isConnected()) {
                String serverUrl = context.getResources().getString(R.string.gzserver);
                tiledLayer = new ArcGISTiledLayer(serverUrl);
            } else {
                tiledLayer = null;
            }
        }
        return tiledLayer;
    }

    /**
     * 移除graphiclayer
     */
    private void removeGraphicLayer() {
        ListenableList<GraphicsOverlay> overlays = mapView.getGraphicsOverlays();
        Iterator<GraphicsOverlay> iterator = overlays.iterator();
        if (iterator.hasNext()) {
            mapView.getGraphicsOverlays().remove(iterator.next());
        }
    }

    /**
     * 添加GraphicsOverlay
     */
    public void addGraphicLayer() {
        graphicsOverlay = new GraphicsOverlay(GraphicsOverlay.RenderingMode.STATIC);
        mapView.getGraphicsOverlays().add(graphicsOverlay);
    }

    /**
     * 加载geodatabase数据
     */
    public void loadGeodatabase() {
        String geopath = "";
        ResourcesUtil util = new ResourcesUtil(mContext);
        List<File> folds = util.getOtmsFiles();
        HashMap<String, File> map = util.getOtmsFoldesFile(folds.get(0).getPath());
        for (String key : map.keySet()) {
            File file = map.get(key);
            if (file.getName().endsWith(".geodatabase")) {
                geopath = file.getPath();
                break;
            }
        }
        final File geoFile = new File(geopath);
        final Geodatabase geodatabase = new Geodatabase(geopath);
        geodatabase.loadAsync();
        geodatabase.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                List<GeodatabaseFeatureTable> tables = geodatabase.getGeodatabaseFeatureTables();
                LayerList layerList = mapView.getMap().getOperationalLayers();
                for (GeodatabaseFeatureTable gdbFeatureTable : tables) {
                    final FeatureLayer layer = new FeatureLayer(gdbFeatureTable);
                    layerList.add(layer);
                    MyFeatureLayer myFeatureLayer = new MyFeatureLayer(geoFile, false, layer);
                    myFeatureLayers.add(myFeatureLayer);
                }
                mapView.invalidate();
            }
        });
    }

    /**
     * 获取FeatureLayer图层样式
     */
    public LayerTemplate getEditSymbo(FeatureLayer flayer) {
        LayerTemplate layerTemplate = new LayerTemplate();
        String typeIdField = ((GeodatabaseFeatureTable) flayer.getFeatureTable()).getTypeIdField();
        if (typeIdField.equals("")) {
            List<FeatureTemplate> featureTemp = ((GeodatabaseFeatureTable) flayer.getFeatureTable()).getFeatureTemplates();
            for (FeatureTemplate featureTemplate : featureTemp) {
                try {
                    ArcGISFeature g = ((GeodatabaseFeatureTable) flayer.getFeatureTable()).createFeature(featureTemplate);
                    layerTemplate.setFeatureLayer(flayer);
                    layerTemplate.setLayerSymbol(flayer.getRenderer().getSymbol(g));
                    layerTemplate.setLayerTemplate(featureTemplate);
                    layerTemplate.setLayerAttributes(g.getAttributes());
                } catch (SQLiteTableLockedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            List<FeatureType> featureTypes = ((GeodatabaseFeatureTable) flayer.getFeatureTable()).getFeatureTypes();
            for (FeatureType featureType : featureTypes) {
                List<FeatureTemplate> templates = featureType.getTemplates();
                for (FeatureTemplate featureTemplate : templates) {
                    try {
                        ArcGISFeature g = ((GeodatabaseFeatureTable) flayer.getFeatureTable()).createFeature(featureTemplate);
                        layerTemplate.setFeatureLayer(flayer);
                        layerTemplate.setLayerSymbol(flayer.getRenderer().getSymbol(g));
                        layerTemplate.setLayerTemplate(featureTemplate);
                        layerTemplate.setLayerAttributes(g.getAttributes());
                    } catch (SQLiteTableLockedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
        return layerTemplate;
    }
}
