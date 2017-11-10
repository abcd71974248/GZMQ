package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.AddLd;
import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.GetSingleDataVO;
import com.hotsun.mqxxgl.busi.service.ldxxgl.AddLdRetrofit;
import com.hotsun.mqxxgl.busi.service.ldxxgl.EditLdRetrofit;
import com.hotsun.mqxxgl.busi.service.ldxxgl.GetLdxxViewRetrofit;
import com.hotsun.mqxxgl.busi.util.UIHelper;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditLdActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;

    EditText ldidText ;
    EditText cunmcText ;
    EditText zumcText ;
    EditText ldmcText ;
    EditText lddzText ;
    EditText dysText ;
    EditText lcsText ;
    EditText fwjgText ;
    EditText xjrqText;
    EditText bzText;
    EditText zxlxText ;
    EditText zxrqText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editld);

        TextView left_bar=(TextView)findViewById(R.id.left_bar);
        left_bar.setOnClickListener(this);

        mContext = EditLdActivity.this;
        Intent intent = getIntent();
        String ldid = intent.getStringExtra("ldid");

        ldidText = (EditText) findViewById(R.id.editld_ldidtext);
        cunmcText = (EditText) findViewById(R.id.editld_cunmctext);
        zumcText = (EditText) findViewById(R.id.editld_zumctext);
        ldmcText = (EditText) findViewById(R.id.editld_ldmctext);
        lddzText = (EditText) findViewById(R.id.editld_lddztext);
        dysText = (EditText) findViewById(R.id.editld_dystext);
        lcsText = (EditText) findViewById(R.id.editld_lcstext);
        fwjgText = (EditText) findViewById(R.id.editld_fwjgtext);
        xjrqText = (EditText) findViewById(R.id.editld_xjrqtext);
        bzText = (EditText) findViewById(R.id.editld_bztext);
        zxlxText = (EditText) findViewById(R.id.editld_zxlxtext);
        zxrqText = (EditText) findViewById(R.id.editld_zxrqtext);

        initView(ldid);


        Button closeBtn = (Button) findViewById(R.id.editld_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button saveBtn = (Button) findViewById(R.id.editld_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLd();
            }
        });
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



        GetLdxxViewRetrofit getLdxxViewRetrofit=new GetLdxxViewRetrofit(EditLdActivity.this);

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

                cunmcText.setText(results.get(0).get("cunmc"));
                zumcText.setText(results.get(0).get("zumc"));
                ldidText.setText(results.get(0).get("ldid"));
                ldmcText.setText(results.get(0).get("ldmc"));
                lddzText.setText(results.get(0).get("ldaddr"));
                dysText.setText(results.get(0).get("cellnum"));
                lcsText.setText(results.get(0).get("floornum"));

                zxlxText.setText(results.get(0).get("zxlxname"));
                fwjgText.setText(results.get(0).get("fwjgname"));
                xjrqText.setText(results.get(0).get("xjrq"));
                zxrqText.setText(results.get(0).get("zxrq"));
                bzText.setText(results.get(0).get("bz"));


            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                UIHelper.ToastErrorMessage(mContext,t.getMessage());
                return;
            }
        });


    }

    private void addLd(){

        EditText ldidText = (EditText) findViewById(R.id.editld_ldidtext);
        String ldid = String.valueOf(ldidText.getText());

        EditText ldmcText = (EditText) findViewById(R.id.editld_ldmctext);
        String ldmc = String.valueOf(ldmcText.getText());

        EditText lddzText = (EditText) findViewById(R.id.editld_lddztext);
        String lddz = String.valueOf(lddzText.getText());

        EditText dysText = (EditText) findViewById(R.id.editld_dystext);
        String dys = String.valueOf(dysText.getText());

        EditText lcsText = (EditText) findViewById(R.id.editld_lcstext);
        String lcs = String.valueOf(lcsText.getText());

        EditText fwjgText = (EditText) findViewById(R.id.editld_fwjgtext);
        String fwjg = String.valueOf(fwjgText.getText());

        EditText xjrqText = (EditText) findViewById(R.id.editld_xjrqtext);
        String xjrq = "2017-11-11";

        EditText bzText = (EditText) findViewById(R.id.editld_bztext);
        String bz = String.valueOf(bzText.getText());

        EditText zxlxText = (EditText) findViewById(R.id.editld_zxlxtext);
        String zxlx = String.valueOf(zxlxText.getText());

        EditText zxrqText = (EditText) findViewById(R.id.editld_zxrqtext);
        String zxrq = "2017-11-11";


        FwLdxx fwLdxx = new FwLdxx();
//        fwLdxx.setZuid(zuid);
        fwLdxx.setLdid(ldid);
        fwLdxx.setLdmc(ldmc);
        fwLdxx.setLdaddr(lddz);
        fwLdxx.setCellnum(dys);
        fwLdxx.setFloornum(lcs);
        fwLdxx.setFwjgdm(fwjg);
        fwLdxx.setXjrq(xjrq);
        fwLdxx.setBz(bz);
        fwLdxx.setZxlxdm(zxlx);
        fwLdxx.setZxrq(zxrq);

        AddLd addLd = new AddLd();
        addLd.setSessionID(MyApplication.tSysUsers.getSessionID());
        addLd.setUserID(MyApplication.tSysUsers.getUserID());
        addLd.setFwLdxx(fwLdxx);

        Gson gson = new Gson();
        String route = gson.toJson(addLd);

        EditLdRetrofit addLdRetrofit = new EditLdRetrofit(EditLdActivity.this);
        Call<ResponseResults> callEditLd = addLdRetrofit.editLd(route);
        callEditLd.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {
                runOnUiThread(new Runnable() {//这是Activity的方法，会在主线程执行任务
                    @Override
                    public void run() {
                        Toast.makeText(EditLdActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });
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