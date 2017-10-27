package com.hotsun.mqxxgl.gis.presenter;

import android.content.Context;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.hotsun.mqxxgl.gis.model.MapTouchListener;

/**
 * Created by li on 2017/10/26.
 *
 */

public class BasePresenter {

    private MapTouchListener myTouchListener;

    public void initMapTouchListener(Context context,MapView mapView){
         myTouchListener = new MapTouchListener(context,mapView);
        mapView.setOnTouchListener(myTouchListener);
    }

}
