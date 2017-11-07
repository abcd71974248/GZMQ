package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.SubMenuBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ConditionText;
import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.ModulePagVO;
import com.hotsun.mqxxgl.busi.presenter.MyLdListAdapter;
import com.hotsun.mqxxgl.busi.service.BusiRetrofitHelper;
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
    private ArrayList<String> list;
    private ListView ldListview;
    private MyLdListAdapter myLdListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldlist);
        mContext = LDActivity.this;

        String mid = getIntent().getStringExtra("mid");


        ldListview = (ListView) findViewById(R.id.ld_list);






        list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            list.add("我" + i + "个条目");
        }
        myLdListAdapter = new MyLdListAdapter(mContext,list);
        ldListview.setAdapter(myLdListAdapter);



        TextView qycxButton = (TextView) findViewById(R.id.qycxbutton);
        qycxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qycxIntent = new Intent(LDActivity.this,DistrictPickerActivity.class);
                startActivity(qycxIntent);
            }
        });
    }



//    private boolean getLdList() {
//
//        String userID= MyApplication.tSysUsers.getUserID();
//        String sessionID=MyApplication.tSysUsers.getSessionID();
//
//        Gson gson = new Gson();
//        ModulePagVO modulePagVO = new ModulePagVO();
//        modulePagVO.setUserID(userID);
//        modulePagVO.setSessionID(sessionID);
//        modulePagVO.setSysid(sysid);
//
//        String route = gson.toJson(modulePagVO);
//
//        ModulePagRetrofit modulePagRetrofit=new ModulePagRetrofit(ModulepageActivity.this);
//
//        Call<ResponseResults> call = modulePagRetrofit.getServer(route);
//        call.enqueue(new Callback<ResponseResults>() {
//            @Override
//            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {
//
//                ResponseResults responseResults=(ResponseResults)response.body();
//                if (responseResults.getStatus().equals("failure"))
//                {
//                    UIHelper.ToastErrorMessage(mContext, "请求服务器出错！");
//                    return;
//                }
//
//                List<Map<String, String>> results=responseResults.getResults();
////                TSysAppMdls tSysAppMdls=
//
////                List<Map<String, Object>> results=responseResults.getResults();
////                String[] imgs={};
////                for(int i=0;i<results.size();i++)
////                {
////
////                    imgs[i]=results.get(i).get("icocss").toString();
////                }
//
//
//                initView(results);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseResults> call, Throwable t) {
//                Log.i("sssss",t.getMessage());
//            }
//        });
//
//        return true;
//
//    }

}
