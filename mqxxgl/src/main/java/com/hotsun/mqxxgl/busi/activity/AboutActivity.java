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

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.DeviceUuidFactory;
import com.hotsun.mqxxgl.busi.model.requestParams.UserLoginVo;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysUsers;
import com.hotsun.mqxxgl.busi.service.UserLoginRetrofit;
import com.hotsun.mqxxgl.busi.util.MD5Utils;
import com.hotsun.mqxxgl.busi.util.UIHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity  implements  View.OnClickListener {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mContext = AboutActivity.this;

        TextView txtTitle= (TextView)findViewById(R.id.text_title);
        txtTitle.setText("关于");
        TextView left_bar=(TextView)findViewById(R.id.left_bar);
        left_bar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        finish();
    }


}
