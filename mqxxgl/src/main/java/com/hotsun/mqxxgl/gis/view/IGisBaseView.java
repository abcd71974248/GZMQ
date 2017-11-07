package com.hotsun.mqxxgl.gis.view;

import android.content.Context;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Grid;
import com.esri.android.map.MapView;

/**
 * GIS基础接口
 * Created by li on 2017/10/26.
 */

public interface IGisBaseView extends ILocationView{

    MapView getMapView();

    Context getContext();

    GraphicsLayer getGraphicsLayer();


}
