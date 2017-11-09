package com.hotsun.mqxxgl.gis.drawTool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureEditResult;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.LineSymbol;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.Symbol;
import com.esri.core.tasks.ags.query.Query;
import com.hotsun.mqxxgl.busi.activity.LdViewActivity;
import com.hotsun.mqxxgl.gis.model.ActionMode;
import com.hotsun.mqxxgl.gis.util.ArcgisUtils;
import com.hotsun.mqxxgl.gis.util.ToastUtil;
import com.hotsun.mqxxgl.gis.view.IDrawTool;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;

import java.util.HashMap;
import java.util.Map;

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

    private int ADDS = 0;
    private int DELETES = 1;
    private int UPDTATES = 2;

    public int getEDITSTATE() {
        return EDITSTATE;
    }

    public void setEDITSTATE(int EDITSTATE) {
        this.EDITSTATE = EDITSTATE;
    }

    private int EDITSTATE=0;

    public Graphic[] getuGraphics() {
        return uGraphics;
    }

    public void setuGraphics(Graphic[] uGraphics) {
        this.uGraphics = uGraphics;
    }

    private Graphic[] uGraphics;

    public IGisBaseView getBaseView() {
        return baseView;
    }

    public void setBaseView(IGisBaseView baseView) {
        this.baseView = baseView;
    }

    private IGisBaseView baseView;
    private DrawToolImpl drawToolImpl = new DrawToolImpl(this);
    private SketchStyle sketchStyle = new SketchStyle();
    private ActionMode actionMode;

    public DrawTool(IGisBaseView baseView) {
        this.baseView = baseView;
        baseView.getMapView().setOnTouchListener(new MapTouchListener(baseView.getContext(), baseView.getMapView()));
        this.markerSymbol = new SimpleMarkerSymbol(Color.RED, 16, SimpleMarkerSymbol.STYLE.CIRCLE);
        this.lineSymbol = new SimpleLineSymbol(Color.RED, 4, SimpleLineSymbol.STYLE.SOLID);
        this.fillSymbol = new SimpleFillSymbol(Color.RED);
        this.fillSymbol.setAlpha(90);
    }

    public void activate(SketchCreationMode drawType, ActionMode mode) {
        this.deactivate();
        this.drawType = drawType;
        this.actionMode = mode;
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
        this.activate(type, actionMode);
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
            if (point.isEmpty()) {
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
                        startPoint = point;
                        envelopeBuilder.setCoords(point.getX(), point.getY(), point.getX(), point.getY());
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
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if(actionMode == ActionMode.MODE_EDIT_ADD){//增加空间数据
                    if(drawType == SketchCreationMode.POINT){
                        //saveFeature(pointBuilder);
                    }
                }
                if (drawType == SketchCreationMode.ENVELOPE || drawType == SketchCreationMode.CIRCLE || drawType == SketchCreationMode.FREEHAND_LINE)
                {

                }

            }

            return super.onTouch(view, event);
        }

        @Override
        public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
            if (active
                    && (drawType == SketchCreationMode.ENVELOPE || drawType == SketchCreationMode.FREEHAND_POLYGON
                    || drawType == SketchCreationMode.FREEHAND_LINE || drawType == SketchCreationMode.CIRCLE)) {
                Point point = baseView.getMapView().toMapPoint(to.getX(), to.getY());
                switch (drawType) {
                    case ENVELOPE:
                        envelopeBuilder.setXMin(startPoint.getX() > point.getX() ? point.getX() : startPoint.getX());
                        envelopeBuilder.setYMin(startPoint.getY() > point.getY() ? point.getY() : startPoint.getY());
                        envelopeBuilder.setXMax(startPoint.getX() < point.getX() ? point.getX() : startPoint.getX());
                        envelopeBuilder.setYMax(startPoint.getY() < point.getY() ? point.getY() : startPoint.getY());
                        baseView.getGraphicsLayer().updateGraphic(graphicID, envelopeBuilder.copy());
                        break;
                    case FREEHAND_POLYGON:
                        polygon.lineTo(point);
                        baseView.getGraphicsLayer().updateGraphic(graphicID, polygon);
                        break;
                    case FREEHAND_LINE:
                        polyline.lineTo(point);
                        baseView.getGraphicsLayer().updateGraphic(graphicID, polyline);
                        break;
                    case CIRCLE:
                        double radius = Math.sqrt(Math.pow(startPoint.getX() - point.getX(), 2)
                                + Math.pow(startPoint.getY() - point.getY(), 2));
                        getCircle(startPoint, radius, polygon);
                        baseView.getGraphicsLayer().updateGraphic(graphicID, polygon);
                        break;
                }
                return false;
            }
            return super.onDragPointerMove(from, to);
        }

        @Override
        public boolean onSingleTap(MotionEvent e) {
            if (active && (drawType == SketchCreationMode.POINT)) {
                Point point = baseView.getMapView().toMapPoint(e.getX(), e.getY());
                if(!pointBuilder.isEmpty()){
                    pointBuilder.setXY(point.getX(), point.getY());
                    baseView.getGraphicsLayer().updateGraphic(graphicID,pointBuilder);
                }else{
                    pointBuilder.setXY(point.getX(), point.getY());
                }

            }
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

    /**
     * 小班保存
     */
    public void saveFeature(Geometry geometry) {
        if (actionMode == ActionMode.MODE_EDIT_ADD) {
            switch (drawType) {
                case POINT:
                    drawToolImpl.addFeaturePoint(pointBuilder);
                    break;
                case FREEHAND_LINE:
                    if(baseView.getMyFeatureLayer().getLayer().getGeometryType().equals(Geometry.Type.POLYGON)){
                        Polygon polygon_al = ArcgisUtils.LineToPolygon(baseView.getContext(), polyline, baseView.getMapView());
                        drawToolImpl.addFeaturePolygon(polygon_al);
                    }else{
                        drawToolImpl.addFeatureLine(polyline);
                    }
                    break;
                case FREEHAND_POLYGON:
                    drawToolImpl.addFeaturePolygon(polygon);
                    break;
            }
        } else if (actionMode == ActionMode.MODE_QIEGE) {
            //saveQgFeature(polyline_all, selectGeometry);
        } else if (actionMode == ActionMode.MODE_XIUBAN) {
            //saveXbFeature(polyline_all);
        } else if (actionMode == ActionMode.MODE_EDIT_ADD_GB) {
            // addFeatureGbMian();
        }

    }

    /**通过楼栋id查询楼栋*/
    public void queryGraphicByLdid(){
        if(baseView.getLdid().equals("")){
            ToastUtil.setToast(baseView.getContext(),"未获取到数据ID");
            return;
        }
        Query query = new Query();
        query.setWhere("LDID = '"+baseView.getLdid()+"'");
        query.setOutFields(new String[]{"*"});
        baseView.getArcGisFeatureLayer().queryFeatures(query, new CallbackListener<FeatureSet>() {
            @Override
            public void onCallback(FeatureSet featureSet) {
                //通过查询若有1条数据则更新，如有多条数据提示用户删除，若无数据进行新增
                int count = featureSet.getObjectIds().length;
                if(count == 0){
                    //新增
                    setEDITSTATE(ADDS);
                }else if(count == 1){
                    //更新
                    Graphic[] graphics = featureSet.getGraphics();
                    int[] ids = new int[count];
                    for(int i=0;i<count;i++){
                        ids[i] = featureSet.getObjectIds()[i];
                    }
                    baseView.getArcGisFeatureLayer().setSelectedGraphics(ids,true);
                    setEDITSTATE(UPDTATES);
                    setuGraphics(graphics);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                //提示网络错误
            }
        });
    }

    /**添加数据*/
    public void addGeometry(){
        if(pointBuilder == null){
            ToastUtil.setToast(baseView.getContext(),"未选定点位位置");
            return;
        }
        if(EDITSTATE == ADDS){
            addOnlineFeature(pointBuilder);
        }else if(EDITSTATE == UPDTATES){
            int count = this.getuGraphics().length;
            if(count > 1){
                deleteOnlineFeature(getuGraphics());
                addOnlineFeature(pointBuilder);
            }else if(count == 1){
                updateOnlineFeature(getuGraphics()[0],pointBuilder);
            }
        }
    }
    /**以当前点为点位信息*/
    public void addCurPointGraphic(){
        if(baseView.getCurPoint() == null){
            ToastUtil.setToast(baseView.getContext(),"未取得当前位置坐标");
            return;
        }
        pointBuilder = baseView.getCurPoint();
        MarkerSymbol mSymbol = new SimpleMarkerSymbol(Color.RED, 25, SimpleMarkerSymbol.STYLE.CIRCLE);
        drawGraphic = new Graphic(this.pointBuilder, mSymbol);
        graphicID = baseView.getGraphicsLayer().addGraphic(drawGraphic);
    }

    /**添加在线的点位数据*/
    public void addOnlineFeature(Geometry point){
        Map<String, Object> objectMap = new HashMap<>();
        Symbol symbol=null;
        int[] ids = baseView.getArcGisFeatureLayer().getGraphicIDs();
        for(int id : ids){
            objectMap = baseView.getArcGisFeatureLayer().getGraphic(id).getAttributes();
            symbol = baseView.getArcGisFeatureLayer().getGraphic(id).getSymbol();
            if(objectMap != null && symbol != null){
                break;
            }
        }
        objectMap.put("LDID",baseView.getLdid());
        Graphic graphic = new Graphic(point,symbol,objectMap);
        Graphic[] graphics = new Graphic[]{graphic};
        applyEdits(ADDS,graphics);
    }

    /**已经存在点位数据*/
    public void updateOnlineFeature(Graphic graphic,Point point){
        Map<String, Object> objectMap = graphic.getAttributes();
        Symbol symbol=graphic.getSymbol();
        Graphic graphic1 = new Graphic(point,symbol,objectMap);
        Graphic[] graphics = new Graphic[]{graphic1};
        applyEdits(UPDTATES,graphics);
    }

    /**删除点位数据*/
    public void deleteOnlineFeature(Graphic[] graphics){
        applyEdits(DELETES,graphics);
    }

    /**编辑点位数据*/
    private void applyEdits(int type,Graphic[] graphics){
        Graphic[] adds = null;
        Graphic[] deletes = null;
        Graphic[] updates = null;
        if(type == ADDS){
            adds = graphics;
        }else if(type == DELETES){
            deletes = graphics;
        }else if(type == UPDTATES){
            updates = graphics;
        }
        baseView.getArcGisFeatureLayer().applyEdits(adds, deletes, updates, new CallbackListener<FeatureEditResult[][]>() {
            @Override
            public void onCallback(FeatureEditResult[][] fets) {
                if(fets[2]!=null && fets[2][0]!=null && fets[2][0].isSuccess()){
                    baseView.getArcGisFeatureLayer().refresh();
                    baseView.getArcGisFeatureLayer().clear();
                    baseView.getGraphicsLayer().removeAll();
                    ToastUtil.setToast(baseView.getContext(),"编辑成功");

                    toResultActivity("true");
                }else{
                    toResultActivity("false");
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("=============",""+throwable.getMessage());
            }
        });
    }

    private void toResultActivity(String state){
        Intent intent = new Intent(baseView.getContext(), LdViewActivity.class);
        intent.putExtra("state",state);
        ((Activity)baseView.getContext()).setResult(1,intent);
        ((Activity) baseView.getContext()).finish();
    }

}
