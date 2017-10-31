package com.hotsun.mqxxgl.gis.model;

import com.esri.arcgisruntime.data.FeatureTemplate;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.symbology.Renderer;
import com.esri.arcgisruntime.symbology.Symbol;

import java.util.Map;

/**
 * Created by li on 2017/10/30.
 * 存放选择图层参数数据
 */

public class LayerTemplate implements java.io.Serializable{


    public FeatureLayer getFeatureLayer() {
        return featureLayer;
    }

    public void setFeatureLayer(FeatureLayer featureLayer) {
        this.featureLayer = featureLayer;
    }

    public Renderer getLayerRenderer() {
        return layerRenderer;
    }

    public void setLayerRenderer(Renderer layerRenderer) {
        this.layerRenderer = layerRenderer;
    }

    public Symbol getLayerSymbol() {
        return layerSymbol;
    }

    public void setLayerSymbol(Symbol layerSymbol) {
        this.layerSymbol = layerSymbol;
    }

    public FeatureTemplate getLayerTemplate() {
        return layerTemplate;
    }

    public void setLayerTemplate(FeatureTemplate layerTemplate) {
        this.layerTemplate = layerTemplate;
    }

    public Map<String, Object> getLayerAttributes() {
        return layerAttributes;
    }

    public void setLayerAttributes(Map<String, Object> layerAttributes) {
        this.layerAttributes = layerAttributes;
    }

    private FeatureLayer featureLayer;
    private Renderer layerRenderer;
    private Symbol layerSymbol;
    private FeatureTemplate layerTemplate;
    private Map<String, Object> layerAttributes;

    public LayerTemplate(){

    }

    public LayerTemplate(FeatureLayer layer,Symbol symbol,Map<String, Object> map){
        this.featureLayer = layer;
        this.layerRenderer = layer.getRenderer();
        this.layerSymbol = symbol;
        this.layerAttributes = map;
    }



}
