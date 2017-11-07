package com.hotsun.mqxxgl.gis.model;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.hotsun.mqxxgl.gis.drawTool.DrawTool;

/**
 * Created by li on 2017/10/26.
 * 地图触摸
 */

public class MyMapTouchListener extends MapOnTouchListener{

    private MapView mapView;
    private Context mContext;
    private DrawTool drawTool;

    public MyMapTouchListener(Context context, MapView mapView, DrawTool drawTool) {
        super(context, mapView);
        this.mapView = mapView;
        this.mContext = context;
        this.drawTool = drawTool;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Point point = screenTomap(event);
        if (point == null || point.isEmpty() ) {
            return false;
        }


        return super.onTouch(view, event);
    }


    /**屏幕点转地图点*/
    private Point screenTomap(MotionEvent event){
        return mapView.toMapPoint(event.getX(),event.getY());
    }
}
