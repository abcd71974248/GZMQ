package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.GetSingleDataVO;
import com.hotsun.mqxxgl.busi.service.ldxxgl.GetLdxxViewRetrofit;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.gis.activity.GisBaseActivity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LdViewActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;

    TextView tv_cunmc;
    TextView tv_zumc;
    TextView tv_ldid;
    TextView tv_ldmc;
    TextView tv_ldaddr;
    TextView tv_cellnum;
    TextView tv_floornum;
    TextView tv_zxlxname;
    TextView tv_fwjgname;
    TextView tv_xjrq;
    TextView tv_zxrq;
    TextView tv_bz;

    private String ldid = "";
    private int requestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busi_activity_ldview);

        TextView txtTitle=(TextView)findViewById(R.id.text_title) ;
        txtTitle.setText("楼栋信息管理");
        ldid=getIntent().getStringExtra("ldid");

        tv_cunmc=(TextView)findViewById(R.id.busi_ldxxview_cunmc);
        tv_zumc=(TextView)findViewById(R.id.busi_ldxxview_zumc);
        tv_ldid=(TextView)findViewById(R.id.busi_ldxxview_ldid);
        tv_ldmc=(TextView)findViewById(R.id.busi_ldxxview_ldmc);
        tv_ldaddr=(TextView)findViewById(R.id.busi_ldxxview_ldaddr);
        tv_cellnum=(TextView)findViewById(R.id.busi_ldxxview_cellnum);
        tv_floornum=(TextView)findViewById(R.id.busi_ldxxview_floornum);
        tv_zxlxname=(TextView)findViewById(R.id.busi_ldxxview_zxlxname);
        tv_fwjgname=(TextView)findViewById(R.id.busi_ldxxview_fwjgname);
        tv_xjrq=(TextView)findViewById(R.id.busi_ldxxview_xjrq);
        tv_zxrq=(TextView)findViewById(R.id.busi_ldxxview_zxrq);
        tv_bz=(TextView)findViewById(R.id.busi_ldxxview_bz);

        TextView textView =(TextView) findViewById(R.id.get_mapdata);
        textView.setOnClickListener(this);

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

                tv_cunmc.setText(results.get(0).get("cunmc"));
                tv_zumc.setText(results.get(0).get("zumc"));
                tv_ldid.setText(results.get(0).get("ldid"));
                tv_ldmc.setText(results.get(0).get("ldmc"));
                tv_ldaddr.setText(results.get(0).get("ldaddr"));
                tv_cellnum.setText(results.get(0).get("cellnum"));
                tv_floornum.setText(results.get(0).get("floornum"));

                tv_zxlxname.setText(results.get(0).get("zxlxname"));
                tv_fwjgname.setText(results.get(0).get("fwjgname"));
                tv_xjrq.setText(results.get(0).get("xjrq"));
                tv_zxrq.setText(results.get(0).get("zxrq"));
                tv_bz.setText(results.get(0).get("bz"));


            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                UIHelper.ToastErrorMessage(mContext,t.getMessage());
                return;
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_mapdata:
                Intent intent = new Intent(LdViewActivity.this, GisBaseActivity.class);
                intent.putExtra("id",ldid);
                startActivityForResult(intent,requestCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == 1){

        }

    }
}
