package com.hotsun.mqxxgl.gis.model;

import com.esri.android.map.FeatureLayer;
import com.esri.core.geodatabase.GeodatabaseFeatureTable;
import com.esri.core.renderer.Renderer;

import java.io.File;
import java.io.Serializable;

/**
 * Created by li on 2017/10/30.
 * 自定义加载FeatureLayer时记录加载的layer
 */

public class MyFeatureLayer implements Serializable{

    public boolean isencrypt() {
        return isencrypt;
    }

    public void setIsencrypt(boolean isencrypt) {
        this.isencrypt = isencrypt;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public FeatureLayer getLayer() {
        return layer;
    }

    public void setLayer(FeatureLayer layer) {
        this.layer = layer;
    }

    public GeodatabaseFeatureTable getTable() {
        return table;
    }

    public void setTable(GeodatabaseFeatureTable table) {
        this.table = table;
    }

    public File getGeodata() {
        return geodata;
    }

    public void setGeodata(File geodata) {
        this.geodata = geodata;
    }

    /**存放数据文件*/
    private File geodata;
    /**geodatabase数据是否加密*/
    private boolean isencrypt;
    /**geodatabase数据图层名称*/
    private String layerName;
    /**geodatabase数据图层renderer*/
    private Renderer renderer;
    /** FeatureLayer*/
    private FeatureLayer layer;
    /** GeodatabaseFeatureTable*/
    private GeodatabaseFeatureTable table;


    public MyFeatureLayer(){

    }

    public MyFeatureLayer(File geodata,boolean isencrypt,FeatureLayer featureLayer){
        this.geodata = geodata;
        this.isencrypt = isencrypt;
        this.layerName = featureLayer.getName();
        this.renderer =featureLayer.getRenderer();
        this.layer = featureLayer;
        this.table = (GeodatabaseFeatureTable) featureLayer.getFeatureTable();
    }
}
