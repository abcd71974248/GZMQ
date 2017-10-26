package com.hotsun.mqxxgl.gis.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.esri.arcgisruntime.mapping.view.MapView;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.model.MyTouchListener;
import com.hotsun.mqxxgl.gis.presenter.BasePresenter;
import com.hotsun.mqxxgl.gis.presenter.LayerPresenter;
import com.hotsun.mqxxgl.gis.presenter.LocationPresenter;
import com.hotsun.mqxxgl.gis.view.IGisBaseView;

public class GisBaseActivity extends AppCompatActivity implements IGisBaseView,View.OnClickListener{

    private MapView mapView;
    private Context mContext;
    private MyTouchListener myTouchListener;
    private BasePresenter basePresenter;
    private LocationPresenter locationPresenter;
    private LayerPresenter layerPresenter = new LayerPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gis_base);

        initView();
        initPresenter();
        initData();

    }

    /**
     * 初始化控件
     */
    private void initView(){
        mContext = GisBaseActivity.this;
        mapView=(MapView) findViewById(R.id.mapview);
        mapView.setAttributionTextVisible(false);

        TextView tv_dw =(TextView) findViewById(R.id.tv_dingwei);
        tv_dw.setOnClickListener(this);
    }

    /**初始化presenter*/
    private void initPresenter(){
        basePresenter = new BasePresenter();
        locationPresenter = new LocationPresenter(mapView);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        locationPresenter.initLocation(mapView);
        layerPresenter.addBaseLayer(mContext,mapView);
        basePresenter.initMapTouchListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_dingwei:
                locationPresenter.zoomTolocation();
                break;
            default:
                break;
        }
    }
}
