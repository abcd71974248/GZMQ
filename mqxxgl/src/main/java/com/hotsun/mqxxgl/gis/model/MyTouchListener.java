package com.hotsun.mqxxgl.gis.model;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;

/**
 * Created by li on 2017/10/26.
 * 地图触摸
 */

public class MyTouchListener extends DefaultMapViewOnTouchListener{

    public MyTouchListener(Context context, MapView mapView) {
        super(context, mapView);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return super.onTouch(view, event);
    }
}
