package com.hotsun.mqxxgl.gis.presenter;


import android.content.Context;

import com.esri.arcgisruntime.mapping.view.MapView;

/**
 * Created by li on 2017/10/26.
 *
 */

public class BasePresenter {

    private Context mContext;
    private MapView mapView;

    public BasePresenter(Context context, MapView mapView){
        this.mContext = context;
        this.mapView = mapView;
    }

}
