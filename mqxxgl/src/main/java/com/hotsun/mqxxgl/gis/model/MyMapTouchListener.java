package com.hotsun.mqxxgl.gis.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SceneView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.hotsun.mqxxgl.gis.drawTool.DrawTool;

/**
 * Created by li on 2017/10/26.
 * 地图触摸
 */

public class MyMapTouchListener extends DefaultMapViewOnTouchListener{

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
        boolean isEmpty = point.isEmpty();
        if (point == null || isEmpty ) {
            return false;
        }



        return super.onTouch(view, event);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Point point = screenTomap(e);
        Graphic graphic = new Graphic(point,new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED,20));
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay(GraphicsOverlay.RenderingMode.STATIC);
        mapView.getGraphicsOverlays().add(graphicsOverlay);
        graphicsOverlay.getGraphics().add(graphic);



        return super.onSingleTapUp(e);
    }


    /**屏幕点转地图点*/
    private Point screenTomap(MotionEvent event){
        int x = Math.round(event.getX());
        int y = Math.round(event.getY());
        android.graphics.Point screenpoint = new android.graphics.Point(x,y);
        Point point = mapView.screenToLocation(screenpoint);
        return point;
    }
}
