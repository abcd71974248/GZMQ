package com.hotsun.mqxxgl.gis.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.esri.android.map.FeatureLayer;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.drawTool.DrawTool;
import com.hotsun.mqxxgl.gis.presenter.BasePresenter;
import com.hotsun.mqxxgl.gis.presenter.LayerPresenter;
import com.hotsun.mqxxgl.gis.presenter.LocationPresenter;
import com.hotsun.mqxxgl.gis.util.ViewUtil;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;
import com.hotsun.mqxxgl.permissions.PermissionsActivity;
import com.hotsun.mqxxgl.permissions.PermissionsChecker;

public class GisBaseActivity extends AppCompatActivity implements IGisBaseView, View.OnClickListener {

    private MapView mapView;
    private Context mContext;
    private DrawTool drawTool;
    private BasePresenter basePresenter;
    private LocationPresenter locationPresenter;
    private LayerPresenter layerPresenter;
    private GraphicsLayer graphicsLayer;
    /*动态检测权限*/
    private static final int REQUEST_CODE = 0; // 权限请求码
    // 所需的定位权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    /*include*/
    private View viewTckz;

    /*定位client*/
    private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gis_activity_base);

        initView();
        initPresenter();
        initData();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        mContext = GisBaseActivity.this;
        mapView = (MapView) findViewById(R.id.mapview);
        TextView tckzView = (TextView) findViewById(R.id.tckz_imageview);
        tckzView.setOnClickListener(this);
        viewTckz = findViewById(R.id.gis_view_tckz);
        ViewUtil.setViewPrams(viewTckz);
        ImageView tckzclose = (ImageView) findViewById(R.id.close_tuceng);
        tckzclose.setOnClickListener(this);
    }

    /**
     * 初始化presenter
     */
    private void initPresenter() {
        mPermissionsChecker = new PermissionsChecker(this);
        basePresenter = new BasePresenter(mContext,mapView);
        locationPresenter = new LocationPresenter(this);
        layerPresenter = new LayerPresenter(mContext,mapView);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        layerPresenter.addBaseLayer(mapView);
        graphicsLayer = layerPresenter.addGraphicLayer();
        locationPresenter.initArcgisLocation(this);
        drawTool = new DrawTool(this);
    }
    /**清除地图的上的所有标绘*/
    public void cleanAll(View view){
        layerPresenter.clearAllGraphics();
    }
    /**当前位置定位*/
    public void mylocation(View view){
        locationPresenter.zoomTolocation();
    }
    /**勾绘小班后重新初始化触摸*/
    public void mapReInitial(View view){

    }
    /**选择Feature*/
    public void selectFeatures(View view){

    }
    /**属性编辑*/
    public void attributeFeture(View view){

    }
    /**修班*/
    public void xiubFeature(View view){

    }
    /**合并图斑*/
    public void mergeFeature(View view){

    }
    /**切割图斑*/
    public void qiegeFeature(View view){

    }
    /**删除图斑*/
    public void deleteFeature(View view){

    }
    /**复制图斑*/
    public void copyFeature(View view){

    }
    /**添加图斑*/
    public void addFeature(View view){

        FeatureLayer layer = layerPresenter.myFeatureLayers.get(0).getLayer();
        layerPresenter.getEditSymbo(layer);
        int drawType = DrawTool.FREEHAND_POLYLINE;
        drawTool.activate(drawType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_tuceng:
                viewTckz.setVisibility(View.GONE);
                break;
            case R.id.tckz_imageview:
                viewTckz.setVisibility(View.VISIBLE);
                layerPresenter.initOtmsData(layerPresenter,viewTckz);
                break;
            default:
                break;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

    @Override
    public Point getCurGpsPoint() {
        return null;
    }

    @Override
    public Point getCurPoint() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationClient != null){
            locationClient.stop();
        }
    }

    @Override
    public MapView getMapView() {
        return mapView;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public GraphicsLayer getGraphicsLayer() {
        return graphicsLayer;
    }
}
