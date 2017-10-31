package com.hotsun.mqxxgl.gis.drawTool;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.esri.arcgisruntime.geometry.EnvelopeBuilder;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointBuilder;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.FillSymbol;
import com.esri.arcgisruntime.symbology.LineSymbol;
import com.esri.arcgisruntime.symbology.MarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.hotsun.mqxxgl.gis.model.MySpatialReference;

/**
 * 图形勾绘工具类
 */
public class DrawTool extends Subject {

    private Context mContext;
    private MapView mapView;
    private GraphicsOverlay tempLayer;
    private MarkerSymbol markerSymbol;
    private LineSymbol lineSymbol;
    private FillSymbol fillSymbol;
    private int drawType;
    private boolean active;
    private PointBuilder pointBuilder;
    private EnvelopeBuilder envelopeBuilder;
    private Polyline polyline;
    private Polygon polygon;
    private Graphic drawGraphic;
    private int graphicID;
    private PointCollection pointCollection;

    public static final int POINT = 1;
    public static final int ENVELOPE = 2;
    public static final int POLYLINE = 3;
    public static final int POLYGON = 4;
    public static final int CIRCLE = 5;
    public static final int ELLIPSE = 6;
    public static final int FREEHAND_POLYGON = 7;
    public static final int FREEHAND_POLYLINE = 8;

    public DrawTool(Context context,MapView mapView) {
        this.mContext = context;
        this.mapView = mapView;
        mapView.setOnTouchListener(new MapTouchListener(context,mapView));
        this.tempLayer = new GraphicsOverlay();
        this.markerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE,Color.BLACK, 16);
        this.lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.DASH,Color.BLACK, 2);
        this.fillSymbol = new SimpleFillSymbol(SimpleFillSymbol.Style.CROSS,Color.BLACK,lineSymbol);
    }

    public void activate(int drawType) {
        this.deactivate();
        this.drawType = drawType;
        this.active = true;
        switch (this.drawType) {
            case DrawTool.POINT:
                pointBuilder = new PointBuilder(MySpatialReference.getMapSpatialReference());
                drawGraphic = new Graphic(this.pointBuilder.toGeometry(), this.markerSymbol);
                break;
            case DrawTool.ENVELOPE:
                this.envelopeBuilder = new EnvelopeBuilder(MySpatialReference.getMapSpatialReference());
                drawGraphic = new Graphic(this.envelopeBuilder.toGeometry(), this.fillSymbol);
                break;
            case DrawTool.POLYGON:
            case DrawTool.CIRCLE:
            case DrawTool.FREEHAND_POLYGON:
                this.pointCollection = new PointCollection(MySpatialReference.getMapSpatialReference());
                polygon = new Polygon(pointCollection);
                drawGraphic = new Graphic(this.polygon, this.fillSymbol);
                break;
            case DrawTool.POLYLINE:
            case DrawTool.FREEHAND_POLYLINE:
                this.pointCollection = new PointCollection(MySpatialReference.getMapSpatialReference());
                polyline = new Polyline(pointCollection);
                drawGraphic = new Graphic(this.polyline, this.lineSymbol);
                break;
        }
        this.tempLayer.getGraphics().add(graphicID,drawGraphic);
    }

    public void deactivate() {
        this.active = false;
        this.drawType = -1;
        this.pointBuilder = null;
        this.envelopeBuilder = null;
        this.polygon = null;
        this.polyline = null;
        this.drawGraphic = null;
    }

    public MarkerSymbol getMarkerSymbol() {
        return markerSymbol;
    }

    public void setMarkerSymbol(MarkerSymbol markerSymbol) {
        this.markerSymbol = markerSymbol;
    }

    public LineSymbol getLineSymbol() {
        return lineSymbol;
    }

    public void setLineSymbol(LineSymbol lineSymbol) {
        this.lineSymbol = lineSymbol;
    }

    public FillSymbol getFillSymbol() {
        return fillSymbol;
    }

    public void setFillSymbol(FillSymbol fillSymbol) {
        this.fillSymbol = fillSymbol;
    }

    private void sendDrawEndEvent() {
        DrawEvent e = new DrawEvent(this, DrawEvent.DRAW_END,DrawTool.this.drawGraphic);
        DrawTool.this.notifyEvent(e);
        int type = this.drawType;
        this.deactivate();
        this.activate(type);
    }

    private Polygon getCircle(Point center, double radius) {
        Point[] points = getPoints(center, radius);
        PointCollection collection = new PointCollection(SpatialReference.create(2343));
        collection.add(points[0]);
        for (int i = 0; i < points.length; i++)
            collection.add(i,points[i]);
        return new Polygon(collection);
    }

    private Point[] getPoints(Point center, double radius) {
        Point[] points = new Point[50];
        double sin;
        double cos;
        double x;
        double y;
        for (double i = 0; i < 50; i++) {
            sin = Math.sin(Math.PI * 2 * i / 50);
            cos = Math.cos(Math.PI * 2 * i / 50);
            x = center.getX() + radius * sin;
            y = center.getY() + radius * cos;
            points[(int) i] = new Point(x, y);
        }
        return points;
    }

    class MapTouchListener extends DefaultMapViewOnTouchListener {

        public MapTouchListener(Context context, MapView mapView) {
            super(context, mapView);
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
