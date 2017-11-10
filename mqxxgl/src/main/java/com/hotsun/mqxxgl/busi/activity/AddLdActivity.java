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
import com.hotsun.mqxxgl.busi.service.ldxxgl.AddLdRetrofit;

import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddLdActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addld);

        TextView left_bar=(TextView)findViewById(R.id.left_bar);
        left_bar.setOnClickListener(this);

        mContext = AddLdActivity.this;
        Intent intent = getIntent();
        String cunmc = intent.getStringExtra("cunmc");
        String zumc = intent.getStringExtra("zumc");
        String zuid = intent.getStringExtra("zuid");

        TextView cunmcText = (TextView) findViewById(R.id.addld_cunmctext);
        cunmcText.setText(cunmc);
        TextView zumcText = (TextView) findViewById(R.id.addld_zumctext);
        zumcText.setText(zumc);
        TextView zuidText = (TextView) findViewById(R.id.addld_zuidtext);
        zuidText.setText(zuid);

        Button closeBtn = (Button) findViewById(R.id.addld_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button saveBtn = (Button) findViewById(R.id.addld_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLd();
            }
        });
    }

    private void addLd(){


        TextView zuidText = (TextView) findViewById(R.id.addld_zuidtext);
        String zuid = String.valueOf(zuidText.getText());

        EditText ldmcText = (EditText) findViewById(R.id.addld_ldmctext);
        String ldmc = String.valueOf(ldmcText.getText());

        EditText lddzText = (EditText) findViewById(R.id.addld_lddztext);
        String lddz = String.valueOf(lddzText.getText());

        EditText dysText = (EditText) findViewById(R.id.addld_dystext);
        String dys = String.valueOf(dysText.getText());

        EditText lcsText = (EditText) findViewById(R.id.addld_lcstext);
        String lcs = String.valueOf(lcsText.getText());

        EditText fwjgText = (EditText) findViewById(R.id.addld_fwjgtext);
        String fwjg = String.valueOf(fwjgText.getText());

        EditText xjrqText = (EditText) findViewById(R.id.addld_xjrqtext);
        String xjrq = "2017-11-11";

        EditText bzText = (EditText) findViewById(R.id.addld_bztext);
        String bz = String.valueOf(bzText.getText());

        EditText zxlxText = (EditText) findViewById(R.id.addld_zxlxtext);
        String zxlx = String.valueOf(zxlxText.getText());

        EditText zxrqText = (EditText) findViewById(R.id.addld_zxrqtext);
        String zxrq = "2017-11-11";


        FwLdxx fwLdxx = new FwLdxx();
        fwLdxx.setZuid(zuid);
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

        AddLdRetrofit addLdRetrofit = new AddLdRetrofit(AddLdActivity.this);
        Call<ResponseResults> callAddLd = addLdRetrofit.addLd(route);
        callAddLd.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> responseZubm) {
                runOnUiThread(new Runnable() {//这是Activity的方法，会在主线程执行任务
                    @Override
                    public void run() {
                        Toast.makeText(AddLdActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
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
