package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.SubMenuBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ConditionText;
import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.GetLdxxVO;
import com.hotsun.mqxxgl.busi.model.requestParams.LdConditionText;
import com.hotsun.mqxxgl.busi.model.requestParams.ModulePagVO;
import com.hotsun.mqxxgl.busi.presenter.MyLdListAdapter;
import com.hotsun.mqxxgl.busi.service.BusiRetrofitHelper;
import com.hotsun.mqxxgl.busi.service.GetLdxxListRetrofit;
import com.hotsun.mqxxgl.busi.service.ModulePagRetrofit;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.gis.service.RetrofitHelper;
import com.hotsun.mqxxgl.gis.util.ToastUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.id.list;


public class LDActivity extends AppCompatActivity  {

    private Context mContext;

    private ListView ldListview;
    private MyLdListAdapter myLdListAdapter;
    private int requestCode = 1;
    private final static int REQUESTCODE = 1; // 返回的结果码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldlist);
        mContext = LDActivity.this;

        String mid = getIntent().getStringExtra("mid");


        ldListview = (ListView) findViewById(R.id.ld_list);





        Button qycxButton = (Button) findViewById(R.id.qycxbutton);
        qycxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qycxIntent = new Intent(LDActivity.this,DistrictPickerActivity.class);
                startActivityForResult(qycxIntent,REQUESTCODE);
            }
        });
        Button qycxButton_onClick = (Button) findViewById(R.id.qycxbutton_onclick);
        qycxButton_onClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLdList();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {
           if (requestCode == REQUESTCODE) {
                    String mCurrentDistrictName = data.getStringExtra("mCurrentDistrictName");
                    String mCurrentZipCode = data.getStringExtra("mCurrentZipCode");
                   //设置结果显示框的显示数值
                   Button qycxButton = (Button) findViewById(R.id.qycxbutton);
                   qycxButton.setText(mCurrentDistrictName);
                   TextView txtldqycxx_zcode=(TextView) findViewById(R.id.ldqycxx_zcode);
                   txtldqycxx_zcode.setText(mCurrentZipCode);
                }
        }


    }

    private boolean getLdList() {

        String userID= MyApplication.tSysUsers.getUserID();
        String sessionID=MyApplication.tSysUsers.getSessionID();

        Gson gson = new Gson();
        GetLdxxVO getLdxxVO = new GetLdxxVO();
        getLdxxVO.setLimit(10);
        getLdxxVO.setStart(0);
//        getLdxxVO.setPage(10);
        getLdxxVO.setUserID(userID);
        getLdxxVO.setSessionID(sessionID);
        TextView txt_zuid=(TextView)findViewById(R.id.ldqycxx_zcode);
        String zuid= String.valueOf(txt_zuid.getText());
        LdConditionText ldConditionText =new LdConditionText();
//        ldConditionText.setCunid(MyApplication.tSysUsers.getCunID());
        ldConditionText.setZuid(zuid);
        ldConditionText.setLdmc("");
        ldConditionText.setUserID(userID);
        getLdxxVO.setConditionText(ldConditionText);

        String route = gson.toJson(getLdxxVO);

        GetLdxxListRetrofit getLdxxListRetrofit=new GetLdxxListRetrofit(LDActivity.this);

        Call<ResponseResults> call = getLdxxListRetrofit.getServer(route);
        call.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                ResponseResults responseResults=(ResponseResults)response.body();
                if (responseResults.getStatus().equals("failure"))
                {
                    UIHelper.ToastErrorMessage(mContext, "请求服务器出错！");
                    return;
                }

                List<Map<String, String>> results=responseResults.getResults();

                myLdListAdapter = new MyLdListAdapter(mContext,results);
                ldListview.setAdapter(myLdListAdapter);



            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });

        return true;

    }

}
