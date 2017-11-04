package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.HomemoduleVO;
import com.hotsun.mqxxgl.busi.model.requestParams.ModulePagVO;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysAppMdls;
import com.hotsun.mqxxgl.busi.presenter.MyGridAdapter;
import com.hotsun.mqxxgl.busi.service.HomemoduleRetrofit;
import com.hotsun.mqxxgl.busi.service.ModulePagRetrofit;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.busi.view.MyGridView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModulepageActivity extends AppCompatActivity implements View.OnClickListener {
    private MyGridView gridview;
    private Context mContext;
    private int[] imgs;
    private String[] img_text ;
    private String[] moduleids ;
    private String sysid;


    private int[] imgColor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulepage);
        TextView txtTitle= (TextView)findViewById(R.id.text_title);
        TextView left_bar=(TextView)findViewById(R.id.left_bar);
        mContext = ModulepageActivity.this;

        imgs= getIntent().getIntArrayExtra("imgs");
        img_text= getIntent().getStringArrayExtra("img_text");
        imgColor= getIntent().getIntArrayExtra("imgColor");
        sysid=getIntent().getStringExtra("sysid");
        moduleids=getIntent().getStringArrayExtra("moduleids");
        txtTitle.setText(getIntent().getStringExtra("sysname"));
        left_bar.setOnClickListener(this);
        getModulepage();

    }


    private void initView(final List<Map<String, String>> results) {
        gridview=(MyGridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MyGridAdapter(this,imgs,img_text,imgColor));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    for(int i=0;i<results.size();i++)
                    {
                        String reponseModuleid=String.valueOf(results.get(i).get("MDLID"));
                        String reponseLimitzt=results.get(i).get("LIMITZT").toString();
                        if (moduleids[position].equals(reponseModuleid))
                        {
                            if(reponseLimitzt.equals("1"))
                            {
                                Intent intent = new Intent();

                                intent.setClass(ModulepageActivity.this,
                                        LDActivity.class);
                                startActivity(intent);
                            }else
                            {
                                UIHelper.ToastErrorMessage(mContext,"你还没有该模块的权限，请联系管理员！");
                            }

                        }
                    }

                }



            }

        });

    }

    private boolean getModulepage() {

        String userID=MyApplication.tSysUsers.getUserID();
        String sessionID=MyApplication.tSysUsers.getSessionID();

        Gson gson = new Gson();
        ModulePagVO modulePagVO = new ModulePagVO();
        modulePagVO.setUserID(userID);
        modulePagVO.setSessionID(sessionID);
        modulePagVO.setSysid(sysid);

        String route = gson.toJson(modulePagVO);

        ModulePagRetrofit modulePagRetrofit=new ModulePagRetrofit(ModulepageActivity.this);

        Call<ResponseResults> call = modulePagRetrofit.getServer(route);
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
//                TSysAppMdls tSysAppMdls=

//                List<Map<String, Object>> results=responseResults.getResults();
//                String[] imgs={};
//                for(int i=0;i<results.size();i++)
//                {
//
//                    imgs[i]=results.get(i).get("icocss").toString();
//                }


                initView(results);


            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });

        return true;

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.left_bar:
                finish();
                break;

        }

    }
}
