package com.hotsun.mqxxgl.busi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.GetCunbm;
import com.hotsun.mqxxgl.busi.model.GetZubm;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.service.GetCunbmRetrofit;
import com.hotsun.mqxxgl.busi.service.GetZubmRetrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.hotsun.mqxxgl.widget.OnWheelChangedListener;
import com.hotsun.mqxxgl.widget.WheelView;
import com.hotsun.mqxxgl.widget.adapters.ArrayWheelAdapter;

public class DistrictPickerActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener {

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_districtpicker);
        setUpViews();
        setUpListener();
        setUpData();

    }

    private void setUpViews() {
//        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void setUpListener() {
        // 添加change事件
//        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件

        mBtnConfirm.setOnClickListener(this);
    }

    private void setUpData() {
        context = this;
//        initProvinceDatas();
//        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(DistrictPickerActivity.this, mProvinceDatas));
//        // 设置可见条目数量
//        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
//        updateCities();
//        updateAreas();

        GetCunbm getCunbm = new GetCunbm();

        getCunbm.setSessionID(MyApplication.tSysUsers.getSessionID());
        getCunbm.setUserID(MyApplication.tSysUsers.getUserID());
        getCunbm.setXzCode(MyApplication.tSysUsers.getXzCode());

        Gson gson = new Gson();
        String route = gson.toJson(getCunbm);

        GetCunbmRetrofit getCunbmRetrofit = new GetCunbmRetrofit(DistrictPickerActivity.this);
        Call<ResponseResults> call = getCunbmRetrofit.getCunbm(route);
        call.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {


                List<Map<String,String>> lists = response.body().getResults();
                String[] cuns = new String[lists.size()];
                for (int i=0;i < lists.size();i++){
                    cuns[i] = lists.get(i).get("cunname").toString();
                    mCunsDatasMap.put(String.valueOf(i),cuns[i]);
                    Log.i("=========",cuns[i]);
                    if(i == 0){
                        mCurrentCityName = lists.get(0).get("cunname").toString();
                        GetZubm getZubm = new GetZubm();
                        getZubm.setSessionID(MyApplication.tSysUsers.getSessionID());
                        getZubm.setUserID(MyApplication.tSysUsers.getUserID());
                        getZubm.setCunID(lists.get(0).get("cunid").toString());

                        Gson gson = new Gson();

                        GetZubmRetrofit getZubmRetrofit = new GetZubmRetrofit(DistrictPickerActivity.this);
                        Call<ResponseResults> callZubm = getZubmRetrofit.getCunbm(gson.toJson(getZubm));
                        callZubm.enqueue(new Callback<ResponseResults>() {
                            @Override
                            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> responseZubm) {

                                List<Map<String,String>> listsZubm = responseZubm.body().getResults();
                                String[] zus = new String[listsZubm.size()];
                                String cunmc = "";
                                for(int j = 0;j < listsZubm.size();j++){
                                    zus[j] = listsZubm.get(j).get("zuname").toString();
                                    cunmc = listsZubm.get(j).get("cunname").toString();
                                    if(j == 0){
                                        mCurrentDistrictName = listsZubm.get(j).get("zuname").toString();
                                        mCurrentZipCode = listsZubm.get(j).get("zuid").toString();
                                    }
                                    mZipcodeDatasMap.put(listsZubm.get(j).get("zuname").toString(),listsZubm.get(j).get("zuid").toString());
                                }
                                mDistrictDatasMap.put(cunmc,zus);

                                mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, zus));
                                mViewDistrict.setCurrentItem(0);
                            }

                            @Override
                            public void onFailure(Call<ResponseResults> call, Throwable t) {
                                Log.i("sssss",t.getMessage());
                            }
                        });
                    }else{
                        GetZubm getZubm = new GetZubm();
                        getZubm.setSessionID(MyApplication.tSysUsers.getSessionID());
                        getZubm.setUserID(MyApplication.tSysUsers.getUserID());
                        getZubm.setCunID(lists.get(i).get("cunid").toString());

                        Gson gson = new Gson();

                        GetZubmRetrofit getZubmRetrofit = new GetZubmRetrofit(DistrictPickerActivity.this);
                        Call<ResponseResults> callZubm = getZubmRetrofit.getCunbm(gson.toJson(getZubm));
                        callZubm.enqueue(new Callback<ResponseResults>() {
                            @Override
                            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> responseZubm) {

                                List<Map<String,String>> listsZubm = responseZubm.body().getResults();
                                String[] zus = new String[listsZubm.size()];
                                String cunmc = "";
                                for(int j = 0;j < listsZubm.size();j++){
                                    zus[j] = listsZubm.get(j).get("zuname").toString();
                                    cunmc = listsZubm.get(j).get("cunname").toString();
                                    mZipcodeDatasMap.put(listsZubm.get(j).get("zuname").toString(),listsZubm.get(j).get("zuid").toString());
                                }

                                mDistrictDatasMap.put(cunmc,zus);

                            }

                            @Override
                            public void onFailure(Call<ResponseResults> call, Throwable t) {
                                Log.i("sssss",t.getMessage());
                            }
                        });
                    }
                }

                mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cuns));
                mViewCity.setCurrentItem(0);
//                ToastUtil.setToast(mContext,response.body().getStatus());
            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }

        });


        String[] cities = {"1"};
        if (cities == null) {
            cities = new String[] { "" };
        }

        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);

        String[] areas = {"2"};

        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
//        // TODO Auto-generated method stub
        if (wheel == mViewCity) {
            int pCurrent = mViewCity.getCurrentItem();
            mCurrentCityName = mCunsDatasMap.get(String.valueOf(pCurrent));
            String[] zus = mDistrictDatasMap.get(mCurrentCityName);
            if (zus == null) {
                zus = new String[] { "" };
            }
            mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, zus));
            mViewDistrict.setCurrentItem(0);
            mCurrentDistrictName = zus[0];
            mCurrentZipCode = mZipcodeDatasMap.get(zus[0]);
            Log.i("+++++++++",String.valueOf(zus.length));
        } else if (wheel == mViewDistrict) {

            String[] zus = mDistrictDatasMap.get(mCurrentCityName);
            mCurrentDistrictName = zus[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(zus[newValue]);
            Log.i("+++++++++",mCurrentDistrictName);
        }

    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);


        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }

        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                showSelectedResult();
                break;
            default:
                break;
        }
    }

    private void showSelectedResult() {
        Toast.makeText(DistrictPickerActivity.this, "当前选中:"+mCurrentCityName+","
                +mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();



        Intent intent = new Intent();
        // 获取用户计算后的结果
        intent.putExtra("mCurrentCityName",mCurrentCityName);//村名称
       intent.putExtra("mCurrentDistrictName", mCurrentDistrictName); //将计算的值回传回去
       intent.putExtra("mCurrentZipCode", mCurrentZipCode); //将计算的值回传回去
       //通过intent对象返回结果，必须要调用一个setResult方法，
       //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
       setResult(2, intent);
       finish(); //结束当前的activity的生命周期


    }


}
