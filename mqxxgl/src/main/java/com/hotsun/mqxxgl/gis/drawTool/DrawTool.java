package com.hotsun.mqxxgl.gis.drawTool;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.LineSymbol;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.hotsun.mqxxgl.gis.view.IDrawTool;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;

/**
 * 图形勾绘工具类
 */
public class DrawTool extends Subject implements IDrawTool {

    private MarkerSymbol markerSymbol;
    private LineSymbol lineSymbol;
    private FillSymbol fillSymbol;
    private SketchCreationMode drawType;
    private boolean active;
    private Point pointBuilder;
    private Envelope envelopeBuilder;
    private Polyline polyline;
    private Polygon polygon;
    private Graphic drawGraphic;
    private int graphicID;
    private Point startPoint;

    private IGisBaseView baseView;
    private DrawToolImpl drawToolImpl = new DrawToolImpl(this);
    private SketchStyle sketchStyle = new SketchStyle();

    public DrawTool(IGisBaseView baseView) {
        this.baseView = baseView;
        baseView.getMapView().setOnTouchListener(new MapTouchListener(baseView.getContext(), baseView.getMapView()));
        this.markerSymbol = new SimpleMarkerSymbol(Color.BLACK, 16, SimpleMarkerSymbol.STYLE.CIRCLE);
        this.lineSymbol = new SimpleLineSymbol(Color.BLACK, 4, SimpleLineSymbol.STYLE.DASH);
        this.fillSymbol = new SimpleFillSymbol(Color.BLACK);
        this.fillSymbol.setAlpha(90);
    }

    public void activate(SketchCreationMode drawType) {
        this.deactivate();
        this.drawType = drawType;
        this.active = true;
        switch (this.drawType) {
            case POINT:
                pointBuilder = new Point();
                drawGraphic = new Graphic(this.pointBuilder, this.markerSymbol);
                break;
            case ENVELOPE:
                this.envelopeBuilder = new Envelope();
                drawGraphic = new Graphic(this.envelopeBuilder, this.fillSymbol);
                break;
            case POLYGON:
            case CIRCLE:
            case FREEHAND_POLYGON:
                this.polygon = new Polygon();
                drawGraphic = new Graphic(this.polygon, this.fillSymbol);
                break;
            case POLYLINE:
            case FREEHAND_LINE:
                this.polyline = new Polyline();
                drawGraphic = new Graphic(this.polyline, this.lineSymbol);
                break;
        }
        graphicID = baseView.getGraphicsLayer().addGraphic(drawGraphic);
    }

    public void deactivate() {
        this.active = false;
        this.drawType = SketchCreationMode.EMPERTY;
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
        DrawEvent e = new DrawEvent(this, DrawEvent.DRAW_END, DrawTool.this.drawGraphic);
        DrawTool.this.notifyEvent(e);
        SketchCreationMode type = this.drawType;
        this.deactivate();
        this.activate(type);
    }

    private void getCircle(Point center, double radius, Polygon circle) {
        circle.setEmpty();
        Point[] points = getPoints(center, radius);
        circle.startPath(points[0]);
        for (int i = 1; i < points.length; i++)
            circle.lineTo(points[i]);
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

    @Override
    public Graphic getDrawGraphic() {
        return drawGraphic;
    }

    private class MapTouchListener extends MapOnTouchListener {

        private MapTouchListener(Context context, MapView mapView) {
            super(context, mapView);
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            Point point = screenTomap(event);
            boolean isEmpty = point.isEmpty();
            if (isEmpty) {
                return false;
            }

            if (active && (drawType == SketchCreationMode.POINT || drawType == SketchCreationMode.ENVELOPE
                    || drawType == SketchCreationMode.CIRCLE
                    || drawType == SketchCreationMode.FREEHAND_LINE || drawType == SketchCreationMode.FREEHAND_POLYGON)
                    && event.getAction() == MotionEvent.ACTION_DOWN) {
                switch (drawType) {
                    case POINT:
                        pointBuilder.setXY(point.getX(), point.getY());
                        break;
                    case ENVELOPE:
                        envelopeBuilder.setCoords(point.getX(), point.getY(),
                                point.getX(), point.getY());
                        break;
                    case CIRCLE:
                        startPoint = point;
                        break;
                    case FREEHAND_POLYGON:
                        polygon.startPath(point);
                        break;
                    case FREEHAND_LINE:
                        polyline.startPath(point);
                        break;
                }
                return true;
            }

            return super.onTouch(view, event);
        }

        @Override
        public boolean onSingleTap(MotionEvent e) {
            Point point = screenTomap(e);
            Graphic graphic = new Graphic(point, new SimpleMarkerSymbol(Color.RED, 20, SimpleMarkerSymbol.STYLE.CIRCLE));
            GraphicsLayer graphicsOverlay = new GraphicsLayer(GraphicsLayer.RenderingMode.STATIC);
            baseView.getMapView().addLayer(graphicsOverlay);
            graphicsOverlay.addGraphic(graphic);

            return super.onSingleTap(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (active
                    && (drawType == SketchCreationMode.ENVELOPE || drawType == SketchCreationMode.FREEHAND_POLYGON
                    || drawType == SketchCreationMode.FREEHAND_LINE || drawType == SketchCreationMode.CIRCLE)) {
                switch (drawType) {

                }
                return false;
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

    }

    /**
     * 屏幕点转地图点
     */
    private Point screenTomap(MotionEvent event) {
        return baseView.getMapView().toMapPoint(event.getX(), event.getY());
    }


}
