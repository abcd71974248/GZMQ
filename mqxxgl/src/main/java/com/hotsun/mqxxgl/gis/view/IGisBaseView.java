package com.hotsun.mqxxgl.gis.view;

import android.app.Activity;
import android.content.Context;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Grid;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.hotsun.mqxxgl.gis.model.LayerTemplate;
import com.hotsun.mqxxgl.gis.model.MyFeatureLayer;

/**
 * GIS基础接口
 * Created by li on 2017/10/26.
 */

public interface IGisBaseView extends ILocationView{

    Activity getActivity();

    MapView getMapView();

    Context getContext();

    GraphicsLayer getGraphicsLayer();

    MyFeatureLayer getMyFeatureLayer();

    LayerTemplate getTemplate();

    ArcGISFeatureLayer getArcGisFeatureLayer();

    String getLdid();

}
