package com.hotsun.mqxxgl.gis.drawTool;

import android.app.Activity;
import android.content.Intent;

import com.esri.android.map.FeatureLayer;
import com.esri.core.geodatabase.GeodatabaseFeature;
import com.esri.core.geodatabase.GeodatabaseFeatureTable;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.MultiPath;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Feature;
import com.esri.core.map.Field;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.Symbol;
import com.esri.core.table.TableException;
import com.hotsun.mqxxgl.busi.activity.LdViewActivity;
import com.hotsun.mqxxgl.gis.model.MySpatialReference;
import com.hotsun.mqxxgl.gis.util.ToastUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 2017/11/7.
 * DrawToolImpl
 */

public class DrawToolImpl {

    private DrawTool drawTool;
    private SketchStyle mSketchStyle;
    /* 记录操作的小班及操作类型 */
    private ArrayList<Map<String, Object>> undonList = new ArrayList<>();

    public DrawToolImpl(DrawTool drawTool){
        this.mSketchStyle = new SketchStyle();
        this.drawTool = drawTool;
    }

    /**添加点位数据*/
    public boolean addCurGraphic(Graphic graphic) {
        Geometry geom = graphic.getGeometry();
        if (!geom.isValid()) {
            ToastUtil.setToast(drawTool.getBaseView().getContext(), "点位无效");
            return false;
        }
        GeodatabaseFeatureTable table = drawTool.getBaseView().getMyFeatureLayer().getTable();
        try {
            long id = table.addFeature(graphic);
            Feature feature = table.getFeature(id);
            Geometry geometry = feature.getGeometry();
            if (geometry == null || geometry.isEmpty() || !geometry.isValid()) {
                table.deleteFeature(id);
                return false;
            } else {
				/* 添加小班后 记录添加小班的id 备撤销时删除 */
                //record(id, "add", editAttributes, geom,drawTool.getBaseView().getMyFeatureLayer().getLayer());
            }
            drawTool.getBaseView().getGraphicsLayer().removeAll();
        } catch (TableException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 新增点
     */
    public void addFeaturePoint(Geometry point) {
        Point point_all = (Point) point;
        Geometry geom = GeometryEngine.simplify(point_all, MySpatialReference.getMapSpatialReference());
        if (!geom.isValid()) {
            ToastUtil.setToast(drawTool.getBaseView().getContext(), "未选择添加位置");
            return;
        }
        try {
            DecimalFormat format = new DecimalFormat("0.000000");
            GeodatabaseFeatureTable table = drawTool.getBaseView().getMyFeatureLayer().getTable();
            GeodatabaseFeature g = table.createFeatureWithTemplate(drawTool.getBaseView().getTemplate().getLayerTemplate(), geom);
            List<Field> pointFields = g.getTable().getFields();
            for (Field field : pointFields) {
                if (field.getAlias().contains("横坐标") || field.getAlias().contains("经度")) {
                    drawTool.getBaseView().getTemplate().getLayerAttributes().put(field.getName(), format.format(point_all.getX()));
                    continue;
                } else if (field.getAlias().contains("纵坐标") || field.getAlias().contains("纬度")) {
                    drawTool.getBaseView().getTemplate().getLayerAttributes().put(field.getName(), format.format(point_all.getY()));
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        addFeatureOnLayer(geom, drawTool.getBaseView().getTemplate().getLayerAttributes());

    }

    /**
     * 新增线
     * polyline_all 绘制的线图形
     */
    public void addFeatureLine(Polyline polyline_all) {
        if (polyline_all == null) {
            ToastUtil.setToast(drawTool.getBaseView().getContext(), "请勾绘线");
            return;
        }

        int size = polyline_all.getPointCount();
        MultiPath multiPath = new Polyline();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                multiPath.startPath(polyline_all.getPoint(i));
            } else {
                multiPath.lineTo(polyline_all.getPoint(i));
            }
        }

        Geometry geom = GeometryEngine.simplify(multiPath, MySpatialReference.getMapSpatialReference());
        addFeatureOnLayer(geom, drawTool.getBaseView().getTemplate().getLayerAttributes());

    }

    /**删除数据*/
    public void deleteFeature(long[] ids){
        try {

            GeodatabaseFeatureTable table = drawTool.getBaseView().getMyFeatureLayer().getTable();
            for(long id : ids){
                table.deleteFeature(id);
                 /* 添加小班后 记录添加小班的id 备撤销时删除 */
                record(id, "delete", null, null,drawTool.getBaseView().getMyFeatureLayer().getLayer());
            }
            drawTool.getBaseView().getGraphicsLayer().removeAll();
        } catch (TableException e) {
            e.printStackTrace();
        }
    }

    /**更新数据*/
    public void updateFeature(Geometry geom,Feature feature){
        try {
            if (geom.isEmpty() || !geom.isValid()) {
                return;
            }

            Envelope envelope = new Envelope();
            geom.queryEnvelope(envelope);
            if (envelope.isEmpty() || !envelope.isValid()) {
                return;
            }

            GeodatabaseFeatureTable table = drawTool.getBaseView().getMyFeatureLayer().getTable();
            Symbol symbol = drawTool.getBaseView().getTemplate().getLayerSymbol();
            Graphic graphic = new Graphic(geom,symbol,feature.getAttributes());
            table.updateFeature(feature.getId(),graphic);

           /* 添加小班后 记录添加小班的id 备撤销时删除 */
            record(feature.getId(), "add", feature.getAttributes(), geom,drawTool.getBaseView().getMyFeatureLayer().getLayer());
            drawTool.getBaseView().getGraphicsLayer().removeAll();
        } catch (TableException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增面
     */
    public void addFeaturePolygon(Polygon polygon) {
        if (polygon == null) {
            ToastUtil.setToast(drawTool.getBaseView().getContext(), "请先勾绘图斑");
            return;
        }
        if (polygon.isEmpty()) {
            return;
        }
        if (polygon.getPointCount() < 3) {
            return;
        }
        if (!polygon.isValid()) {
            return;
        }

        Geometry geom = GeometryEngine.simplify(polygon, MySpatialReference.getMapSpatialReference());
        addFeatureOnLayer(polygon, drawTool.getBaseView().getTemplate().getLayerAttributes());
    }

    /**
     * 添加feature在图层上
     * geom 绘制的图形
     * selFeatureAts  图层属性
     */
    public void addFeatureOnLayer(Geometry geom, Map<String, Object> selFeatureAts) {
        try {
            if (geom.isEmpty() || !geom.isValid()) {
                return;
            }

            Envelope envelope = new Envelope();
            geom.queryEnvelope(envelope);
            if (envelope.isEmpty() || !envelope.isValid()) {
                return;
            }

            GeodatabaseFeatureTable table = drawTool.getBaseView().getMyFeatureLayer().getTable();
            Symbol symbol = drawTool.getBaseView().getTemplate().getLayerSymbol();
            // symbol为null也可以 why？
            Map<String, Object> editAttributes = null;
            if (selFeatureAts == null) {
                editAttributes = drawTool.getBaseView().getTemplate().getLayerAttributes();
            } else {
                editAttributes = selFeatureAts;
            }
            //TODO
            String type = "yyyyMMddHHmmss";
            SimpleDateFormat format = new SimpleDateFormat(type);
            String str = format.format(new Date());
            editAttributes.put("WYBH", str);

            Graphic addedGraphic = new Graphic(geom, symbol, editAttributes);
            long id = table.addFeature(addedGraphic);

            Feature feature = table.getFeature(id);
            Geometry geometry = feature.getGeometry();
            if (geometry == null || geometry.isEmpty() || !geometry.isValid()) {
                table.deleteFeature(id);
            } else {
				/* 添加小班后 记录添加小班的id 备撤销时删除 */
                record(id, "add", editAttributes, geom,drawTool.getBaseView().getMyFeatureLayer().getLayer());
            }
            drawTool.getBaseView().getGraphicsLayer().removeAll();
        } catch (TableException e) {
            e.printStackTrace();
        }
    }

    /**
     * 记录变化的 以备撤销使用
     * @param id             小班id
     * @param type           修改类型 添加 更新 删除
     * @param attr           变动小班的属性信息
     * @param geom           变动小班
     * @param layer          变动小班所在图层
     */
    private void record(long id, String type, Map<String, Object> attr,Geometry geom, FeatureLayer layer) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("type", type);
        map.put("attribute", attr);
        map.put("geometry", geom);
        map.put("layer", layer);
        undonList.add(map);
    }


}
