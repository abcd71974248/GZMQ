package com.hotsun.mqxxgl.gis.presenter;

import android.content.Context;
import android.database.sqlite.SQLiteTableLockedException;
import android.view.View;
import android.widget.ExpandableListView;

import com.esri.android.map.FeatureLayer;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.TiledLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.core.geodatabase.Geodatabase;
import com.esri.core.geodatabase.GeodatabaseFeature;
import com.esri.core.geodatabase.GeodatabaseFeatureTable;
import com.esri.core.geometry.Geometry;
import com.esri.core.map.FeatureTemplate;
import com.esri.core.map.FeatureType;
import com.esri.core.table.TableException;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.adapter.ExpandableAdapter;
import com.hotsun.mqxxgl.gis.model.LayerTemplate;
import com.hotsun.mqxxgl.gis.model.MyFeatureLayer;
import com.hotsun.mqxxgl.gis.service.LiveNetworkMonitor;
import com.hotsun.mqxxgl.gis.service.NetworkMonitor;
import com.hotsun.mqxxgl.gis.util.ResourcesUtil;

import java.io.File;
import java.io.FileNotFoundException;
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
    private GraphicsLayer graphicsOverlay;
    public List<MyFeatureLayer> myFeatureLayers = new ArrayList<>();
    private HashMap<String,Boolean> checkStates = new HashMap<>();

    public LayerPresenter(Context context, MapView mapView) {
        this.mContext = context;
        this.mapView = mapView;
        graphicsOverlay = new GraphicsLayer(GraphicsLayer.RenderingMode.STATIC);
    }

    /**
     * 加载地图
     */
    public void addBaseLayer(MapView mapView) {
        TiledLayer tiledLayer = getTitleLayer(mContext);
        mapView.addLayer(tiledLayer);
        mapView.setMapBackground(0xffffff, 0xffffff, 3, 3);
    }

    /**
     * 获取离线地图
     */
    private TiledLayer getTitleLayer(Context context) {
        ResourcesUtil resourcesUtil = ResourcesUtil.getInstance();
        String titlepath = resourcesUtil.getBaseTitlePath(context);
        TiledLayer tiledLayer;
        if (new File(titlepath).exists()) {
            //离线数据存在
            tiledLayer = new ArcGISLocalTiledLayer(titlepath);
        } else {
            NetworkMonitor networkMonitor = new LiveNetworkMonitor(context);
            if (networkMonitor.isConnected()) {
                String serverUrl = context.getResources().getString(R.string.gzserver);
                tiledLayer = new ArcGISTiledMapServiceLayer(serverUrl);
            } else {
                tiledLayer = null;
            }
        }
        return tiledLayer;
    }

    /**添加同步要素服务*/
    public ArcGISFeatureLayer addFeatureLayer(){
        String fserverUrl = mContext.getResources().getString(R.string.ld_fserver);
        ArcGISFeatureLayer arcGISFeatureLayer = new ArcGISFeatureLayer(fserverUrl, ArcGISFeatureLayer.MODE.ONDEMAND);
        mapView.addLayer(arcGISFeatureLayer);
        return arcGISFeatureLayer;
    }

    /**
     * 移除graphiclayer
     */
    private void removeGraphicLayer() {
        Layer[] overlays = mapView.getLayers();
        for (Layer layer : overlays) {
            if(layer instanceof GraphicsLayer){
                mapView.removeLayer(layer);
            }
        }
    }

    public void clearAllGraphics(){
        graphicsOverlay.removeAll();
    }

    /**
     * 添加GraphicsOverlay
     */
    public GraphicsLayer addGraphicLayer() {
        graphicsOverlay = new GraphicsLayer(GraphicsLayer.RenderingMode.STATIC);
        mapView.addLayer(graphicsOverlay);
        return graphicsOverlay;
    }

    /**
     * 加载geodatabase数据
     */
    public void loadGeodatabase(File geoFile) {
        Geodatabase geodatabase = null;
        try {
            geodatabase = new Geodatabase(geoFile.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(geodatabase != null){
            addGeodatabase(geodatabase,geoFile);
        }
    }

    /**定位到图层所在位置*/
    public void zoomToLayer(File file){
        for(MyFeatureLayer myFeatureLayer : myFeatureLayers){
            if(myFeatureLayer.getGeodata().equals(file)){
                mapView.setExtent(myFeatureLayer.getLayer().getFullExtent());
            }
        }
    }

    private void addGeodatabase(Geodatabase geodatabase,File geoFile){
        List<GeodatabaseFeatureTable> tables = geodatabase.getGeodatabaseTables();
        for (GeodatabaseFeatureTable gdbFeatureTable : tables) {
            if (gdbFeatureTable.hasGeometry()){
                FeatureLayer layer = new FeatureLayer(gdbFeatureTable);
                mapView.addLayer(layer);
                MyFeatureLayer myFeatureLayer = new MyFeatureLayer(geoFile, false, layer);
                myFeatureLayers.add(myFeatureLayer);
            }
        }
    }

    /**移除加载的geodatabase*/
    public void removeGeodatabase(File file){
        Iterator<MyFeatureLayer> iterator = myFeatureLayers.iterator();
        while (iterator.hasNext()){
            MyFeatureLayer myFeatureLayer = iterator.next();
            if(myFeatureLayer.getGeodata().equals(file)){
                mapView.removeLayer(myFeatureLayer.getLayer());
                iterator.remove();
            }
        }
    }

    /**添加Geometry*/
    public void addGeometry(final Geometry geometry, final MyFeatureLayer myFeatureLayer){
        final FeatureLayer layer = myFeatureLayer.getLayer();
    }

    /**初始化otms数据*/
    public void initOtmsData(LayerPresenter presenter,View viewTckz){

        ExpandableListView listView = (ExpandableListView) viewTckz.findViewById(R.id.tc_expandlistview);
        initExpandableListView(presenter,listView);
    }
    /**绑定ExpandableListView数据*/
    private void initExpandableListView(LayerPresenter presenter,ExpandableListView listView){
        ResourcesUtil util = ResourcesUtil.getInstance();
        List<File> folds = util.getOtmsFiles(mContext);
        HashMap<String, List<File>> map = util.getOtmsFoldesFile(folds);
        ExpandableAdapter adapter = new ExpandableAdapter(mContext,folds,map,checkStates,presenter);
        listView.setAdapter(adapter);
        checkStates = adapter.getCheckStates();
    }

    public interface ICheckBox{
        HashMap<String,Boolean> getCheckStates();
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
                    GeodatabaseFeature g = ((GeodatabaseFeatureTable) flayer.getFeatureTable()).createFeatureWithTemplate(featureTemplate,null);
                    layerTemplate.setFeatureLayer(flayer);
                    layerTemplate.setLayerSymbol(flayer.getRenderer().getSymbol(g));
                    layerTemplate.setLayerTemplate(featureTemplate);
                    layerTemplate.setLayerAttributes(g.getAttributes());
                } catch (SQLiteTableLockedException | TableException e) {
                    e.printStackTrace();
                }
            }
        } else {
            List<FeatureType> featureTypes = ((GeodatabaseFeatureTable) flayer.getFeatureTable()).getFeatureTypes();
            for (FeatureType featureType : featureTypes) {
                FeatureTemplate[] templates = featureType.getTemplates();
                for (FeatureTemplate featureTemplate : templates) {
                    try {
                        GeodatabaseFeature g = ((GeodatabaseFeatureTable) flayer.getFeatureTable()).createFeatureWithTemplate(featureTemplate,null);
                        layerTemplate.setFeatureLayer(flayer);
                        layerTemplate.setLayerSymbol(flayer.getRenderer().getSymbol(g));
                        layerTemplate.setLayerTemplate(featureTemplate);
                        layerTemplate.setLayerAttributes(g.getAttributes());
                    } catch (SQLiteTableLockedException | TableException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
        return layerTemplate;
    }
}
