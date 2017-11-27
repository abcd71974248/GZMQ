package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.GetOftenModule;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.GetSingleDataVO;
import com.hotsun.mqxxgl.busi.presenter.OftenMdlListAdapter;
import com.hotsun.mqxxgl.busi.service.AddOftenModuleRetrofit;
import com.hotsun.mqxxgl.busi.service.GetNoOftenModuleRetrofit;
import com.hotsun.mqxxgl.busi.service.GetOftenModuleRetrofit;
import com.hotsun.mqxxgl.busi.view.RefreshListView;
import com.hotsun.mqxxgl.gis.activity.GisBaseActivity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OftenUseActivity extends AppCompatActivity  implements  View.OnClickListener {

    private Context mContext;
    private OftenMdlListAdapter oftenlistAdapt;
    private OftenMdlListAdapter oftenlistnoAdapt;
    private ListView oftenlist;
    private ListView oftenlistno;
    private int oftensize;

    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oftenlist);
        mContext = OftenUseActivity.this;

        TextView txtTitle= (TextView)findViewById(R.id.text_title);
        txtTitle.setText("常用功能设置");
        TextView left_bar=(TextView)findViewById(R.id.left_bar);
        left_bar.setOnClickListener(this);

        oftenlist = (ListView) findViewById(R.id.refresh_oftenlist);
        oftenlistno = (ListView) findViewById(R.id.refresh_oftenlistno);
        btnSave = (Button) findViewById(R.id.oftenlist_btn_save);
        btnSave.setOnClickListener(this);

        initView();
    }

    public void initView(){
        Gson gson = new Gson();
        GetOftenModule getOftenModule = new GetOftenModule();
        getOftenModule.setUserID(MyApplication.tSysUsers.getUserID());
        getOftenModule.setSessionID(MyApplication.tSysUsers.getSessionID());

        String route = gson.toJson(getOftenModule);

        GetOftenModuleRetrofit getOftenModuleRetrofit = new GetOftenModuleRetrofit(OftenUseActivity.this);
        Call<ResponseResults> call = getOftenModuleRetrofit.getServer(route);
        call.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                ResponseResults responseResults=(ResponseResults)response.body();

                List<Map<String, String>> results=responseResults.getResults();

                oftenlistAdapt = new OftenMdlListAdapter(mContext,results,false);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) oftenlist.getLayoutParams();
                oftensize = results.size();
                if(results.size() >= 4){
                    params.height = 340;
                }else{
                    params.height = results.size() * 85;
                }
                oftenlist.setLayoutParams(params);
                oftenlist.setAdapter(oftenlistAdapt);
            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });

        GetNoOftenModuleRetrofit getNoOftenModuleRetrofit = new GetNoOftenModuleRetrofit(OftenUseActivity.this);
        Call<ResponseResults> callNo = getNoOftenModuleRetrofit.getServer(route);
        callNo.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                ResponseResults responseResults=(ResponseResults)response.body();

                List<Map<String, String>> results=responseResults.getResults();

                oftenlistnoAdapt = new OftenMdlListAdapter(mContext,results,true);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) oftenlistno.getLayoutParams();
                if(results.size() >= 5){
                    if(oftensize < 4){
                        params.height = 430+(4-oftensize)*85;
                    }else{
                        params.height = 430;
                    }
                }else{
                    if(oftensize < 4){
                        params.height = (results.size()+4-oftensize) * 85;
                    }else{
                        params.height = results.size() * 85;
                    }
                }
                oftenlistno.setLayoutParams(params);
                oftenlistno.setAdapter(oftenlistnoAdapt);
            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });
        setResult(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.oftenlist_btn_save:
                String mdlids = oftenlistnoAdapt.getMdlids();

                if(mdlids.equals("")){
                    Toast.makeText(OftenUseActivity.this, "请选择需要添加的功能！", Toast.LENGTH_SHORT).show();
                    return;
                }

                String userID= MyApplication.tSysUsers.getUserID();
                String sessionID=MyApplication.tSysUsers.getSessionID();

                Gson gson = new Gson();
                GetSingleDataVO addOften = new GetSingleDataVO();


                addOften.setUserID(userID);
                addOften.setSessionID(sessionID);
                addOften.setDataid(mdlids);

                String route = gson.toJson(addOften);

                AddOftenModuleRetrofit addOftenModuleRetrofit = new AddOftenModuleRetrofit(OftenUseActivity.this);
                Call<ResponseResults> call = addOftenModuleRetrofit.getServer(route);
                call.enqueue(new Callback<ResponseResults>() {
                    @Override
                    public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                        runOnUiThread(new Runnable() {//这是Activity的方法，会在主线程执行任务
                            @Override
                            public void run() {
                                initView();
                                Toast.makeText(OftenUseActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ResponseResults> call, Throwable t) {
                        Log.i("sssss",t.getMessage());
                    }
                });
                break;
            case R.id.left_bar:
                finish();
                break;
        }
    }


}
