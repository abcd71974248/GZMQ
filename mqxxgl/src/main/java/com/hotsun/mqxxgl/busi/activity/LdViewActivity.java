package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.GetSingleDataVO;
import com.hotsun.mqxxgl.busi.service.ldxxgl.GetLdxxViewRetrofit;
import com.hotsun.mqxxgl.busi.util.UIHelper;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LdViewActivity extends AppCompatActivity{

    private Context mContext;

//    TextView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busi_activity_ldview);

        TextView txtTitle=(TextView)findViewById(R.id.text_title) ;
        txtTitle.setText("楼栋信息管理");
        String ldid=getIntent().getStringExtra("ldid");

        TextView ldview=(TextView)findViewById(R.id.addld_cunmctext);

        ldview.setText(ldid);

        initView(ldid);

    }
    private void initView(String ldid) {


        if (MyApplication.tSysUsers==null)
        {
            UIHelper.ToastErrorMessage(mContext,"操作失败，请重新登录!");
            return ;
        }
        String userID= MyApplication.tSysUsers.getUserID();
        String sessionID=MyApplication.tSysUsers.getSessionID();

        Gson gson = new Gson();
        GetSingleDataVO getLdxxVO = new GetSingleDataVO();


        getLdxxVO.setUserID(userID);
        getLdxxVO.setSessionID(sessionID);
        getLdxxVO.setDataid(ldid);

        String route = gson.toJson(getLdxxVO);



        GetLdxxViewRetrofit getLdxxViewRetrofit=new GetLdxxViewRetrofit(LdViewActivity.this);

        Call<ResponseResults> call = getLdxxViewRetrofit.getServer(route);
        call.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                ResponseResults responseResults=(ResponseResults)response.body();
                if (responseResults.getStatus().equals("failure"))
                {
                    UIHelper.ToastErrorMessage(mContext, "请求服务器出错！");
                    return;
                }
                final List<Map<String, String>> results=responseResults.getResults();




            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });


    }



}
