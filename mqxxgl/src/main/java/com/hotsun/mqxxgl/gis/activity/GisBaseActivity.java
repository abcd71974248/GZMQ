package com.hotsun.mqxxgl.gis.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.dialog.LayerSelectDialog;
import com.hotsun.mqxxgl.gis.drawTool.DrawTool;
import com.hotsun.mqxxgl.gis.drawTool.SketchCreationMode;
import com.hotsun.mqxxgl.gis.model.ActionMode;
import com.hotsun.mqxxgl.gis.model.LayerTemplate;
import com.hotsun.mqxxgl.gis.model.MyFeatureLayer;
import com.hotsun.mqxxgl.gis.presenter.BasePresenter;
import com.hotsun.mqxxgl.gis.presenter.LayerPresenter;
import com.hotsun.mqxxgl.gis.presenter.LocationPresenter;
import com.hotsun.mqxxgl.gis.sync.GDBUtil;
import com.hotsun.mqxxgl.gis.util.DialogUtil;
import com.hotsun.mqxxgl.gis.util.NetworkUtil;
import com.hotsun.mqxxgl.gis.util.ToastUtil;
import com.hotsun.mqxxgl.gis.util.ViewUtil;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;
import com.hotsun.mqxxgl.permissions.PermissionsActivity;
import com.hotsun.mqxxgl.permissions.PermissionsChecker;

public class GisBaseActivity extends AppCompatActivity implements IGisBaseView, View.OnClickListener {

    private MapView mapView;
    private Context mContext;
    private DrawTool drawTool;
    private LocationPresenter locationPresenter;
    private LayerPresenter layerPresenter;
    private GraphicsLayer graphicsLayer;
    /*编辑图层数据*/
    private MyFeatureLayer myFeatureLayers;
    private LayerTemplate layerTemplate;
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

    private ArcGISFeatureLayer arcGISFeatureLayer;

    private String ld_id = "";

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
        TextView downloadview =(TextView) findViewById(R.id.data_download);
        downloadview.setOnClickListener(this);
        TextView dataSyncView =(TextView) findViewById(R.id.data_sync);
        dataSyncView.setOnClickListener(this);
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
        BasePresenter basePresenter = new BasePresenter(mContext, mapView);
        locationPresenter = new LocationPresenter(this);
        layerPresenter = new LayerPresenter(mContext,mapView);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        layerPresenter.addBaseLayer(mapView);
        if(NetworkUtil.hasInternet(this)){
            arcGISFeatureLayer = layerPresenter.addFeatureLayer();
        }
        graphicsLayer = layerPresenter.addGraphicLayer();
        locationPresenter.initArcgisLocation(this);
        drawTool = new DrawTool(this);
        if(getIntent() != null){
            ld_id = getIntent().getStringExtra("id");//520623000084
            ld_id = "520623000084";
        }

        if(NetworkUtil.hasInternet(GisBaseActivity.this)){
            drawTool.queryGraphicByLdid();
        }

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
        Graphic[] graphics = new Graphic[]{drawTool.getDrawGraphic()};
        drawTool.deleteOnlineFeature(graphics);
    }
    /**复制图斑*/
    public void copyFeature(View view){

    }
    /**保存按钮*/
    public void saveFeature(View view){
        drawTool.addGeometry();
    }
    /**s使用当前点位添加数据*/
    public void addFeatureCurrut(View view){
        drawTool.addCurPointGraphic();
    }

    /**添加数据*/
    public void addFeature(View view){
        if(NetworkUtil.hasInternet(this)){
            if(arcGISFeatureLayer == null){
                ToastUtil.setToast(mContext,"请先加载矢量数据");
                return;
            }
        }else{
            int size = layerPresenter.myFeatureLayers.size();
            if(size == 0){
                ToastUtil.setToast(mContext,"请先加载矢量数据");
                return;
            }
            if(size == 1){
                myFeatureLayers = layerPresenter.myFeatureLayers.get(0);
                layerTemplate = layerPresenter.getEditSymbo(myFeatureLayers.getLayer());
            }else{
                LayerSelectDialog selectDialog = new LayerSelectDialog(mContext,R.style.FetionTheme_Dialog,layerPresenter);
                DialogUtil.setDialogCenter(mContext,selectDialog,0.5,0.8);
            }

        }
        SketchCreationMode drawType = SketchCreationMode.POINT;
        drawTool.activate(drawType, ActionMode.MODE_EDIT_ADD);

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
            case R.id.data_download:
                new ConnectToServer().execute("downloadGdb");
                break;
            case R.id.data_sync:
                new ConnectToServer().execute("syncGdb");
                break;
            default:
                break;
        }
    }

    /**
     * Connect to server to synchronize edits back or download features locally
     */
    public static ProgressDialog progress;
    public class ConnectToServer extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(GisBaseActivity.this);
            progress = ProgressDialog.show(GisBaseActivity.this, "","Processing... Please wait...");
        }

        @Override
        protected Void doInBackground(String... params) {
            if (params[0].equals("syncGdb")) {
                new GDBUtil(GisBaseActivity.this).synchronize(GisBaseActivity.this);
            } else if (params[0].equals("downloadGdb")) {
                new GDBUtil(GisBaseActivity.this).downloadData(GisBaseActivity.this);
            }
            return null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        }else{
            //你想做的事情
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }else{

        }
    }

    @Override
    public Point getCurGpsPoint() {
        return locationPresenter.getCurGpsPoint();
    }

    @Override
    public Point getCurPoint() {
        return locationPresenter.getCurPoint();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationClient != null){
            locationClient.stop();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
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

    @Override
    public MyFeatureLayer getMyFeatureLayer() {
        return myFeatureLayers;
    }

    @Override
    public LayerTemplate getTemplate() {
        return layerTemplate;
    }

    @Override
    public ArcGISFeatureLayer getArcGisFeatureLayer() {
        return arcGISFeatureLayer;
    }

    @Override
    public String getLdid() {
        return ld_id;
    }

}
