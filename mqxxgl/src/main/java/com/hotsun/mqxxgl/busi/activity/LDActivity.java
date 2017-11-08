package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.SubMenuBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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

    final int[] ldListxx = new int[] { R.id.ldga_item_ldid, R.id.ldxx_zumc, R.id.ldxx_ldname };



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

        LinearLayout addldButton = (LinearLayout) findViewById(R.id.busi_add_ldxx);
        addldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLd();
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
                    String mCurrentCityName = data.getStringExtra("mCurrentCityName");
                   //设置结果显示框的显示数值
                   Button qycxButton = (Button) findViewById(R.id.qycxbutton);
                   qycxButton.setText(mCurrentDistrictName);
                   TextView txtldqycxx_zcode=(TextView) findViewById(R.id.ldqycxx_zcode);
                   txtldqycxx_zcode.setText(mCurrentZipCode);
                   TextView ldqycxCunmc=(TextView) findViewById(R.id.ldqycx_cunmc);
                   ldqycxCunmc.setText(mCurrentCityName);
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

        getLdxxVO.setUserID(userID);
        getLdxxVO.setSessionID(sessionID);
        TextView txt_zuid=(TextView)findViewById(R.id.ldqycxx_zcode);
        UIHelper.ToastMessage(mContext,txt_zuid.getText().toString());
        String zuid= String.valueOf(txt_zuid.getText());
//        LdConditionText ldConditionText =new LdConditionText();
//        ldConditionText.setCunid(MyApplication.tSysUsers.getCunID());
        getLdxxVO.setZuid(zuid);
//        ldConditionText.setLdmc("");
//        ldConditionText.setUserID(userID);

//        getLdxxVO.setConditionText(ldConditionText);
        getLdxxVO.setLdmc("");
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

                ldListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                      TextView txtldga=(TextView)ldListview.getChildAt(position).findViewById(R.id.activity_cunmc);
//                      Object asas=  parent.getAdapter().getItem(position);
//                        UIHelper.ToastMessage(mContext,"dsadad");

                           Intent intent = new Intent();
                        intent.putExtra("ldid",txtldga.getText());

                           intent.setClass(LDActivity.this, LdViewActivity.class);

                           startActivity(intent);





                    }

                });


            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });

        return true;

    }

    private void addLd(){
        TextView qycxCunmc = (TextView) findViewById(R.id.ldqycx_cunmc);
        Button qycxZumc = (Button) findViewById(R.id.qycxbutton);
        TextView qycxZuid = (TextView) findViewById(R.id.ldqycxx_zcode);
        String cunmc = String.valueOf(qycxCunmc.getText());
        String zumc = String.valueOf(qycxZumc.getText());
        String zuid = String.valueOf(qycxZuid.getText());

        Intent intent = new Intent(LDActivity.this,AddLdActivity.class);
        intent.putExtra("cunmc",cunmc);
        intent.putExtra("zumc",zumc);
        intent.putExtra("zuid",zuid);
        startActivity(intent);
    }
}
