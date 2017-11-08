package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ConditionText;
import com.hotsun.mqxxgl.busi.model.DeviceUuidFactory;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.UserLoginVo;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysUsers;
import com.hotsun.mqxxgl.busi.service.BusiRetrofitHelper;
import com.hotsun.mqxxgl.busi.service.UserLoginRetrofit;
import com.hotsun.mqxxgl.busi.util.MD5Utils;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.gis.service.LiveNetworkMonitor;
import com.hotsun.mqxxgl.gis.service.NetworkMonitor;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserLoginActivity extends AppCompatActivity  implements  View.OnClickListener {

    private Context mContext;
    private EditText edtUserbh, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        mContext = UserLoginActivity.this;
        edtUserbh = (EditText) findViewById(R.id.edtTxt_userlogin_userbh);
        edtPassword = (EditText) findViewById(R.id.edtTxt_userlogin_usermm);

        TextView txtTab = (TextView) findViewById(R.id.left_bar);
        txtTab.setVisibility(View.INVISIBLE);

        Button btnSubmit = (Button) findViewById(R.id.btn_userlogin_submit);
        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        userLogin();
    }

    private boolean userLogin() {
        String userNumber = edtUserbh.getText().toString();
        String password = edtPassword.getText().toString();
        DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(mContext);
        String uuid = deviceUuidFactory.getDeviceUuid().toString();

        if (userNumber.trim().length() < 2) {
            UIHelper.ToastErrorMessage(mContext, "用户编号不能为空");
            return false;
        }
        if (password.trim().length() < 1) {
            UIHelper.ToastErrorMessage(mContext, "用户密码不能为空");
            return false;
        }

        Gson gson = new Gson();
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserNumber(userNumber);
        String md5Password= MD5Utils.md5(password);
        userLoginVo.setPassword(md5Password);
        userLoginVo.setUUID(uuid);
        String route = gson.toJson(userLoginVo);


        UserLoginRetrofit userLoginRetrofit=new UserLoginRetrofit(UserLoginActivity.this);
        Call<TSysUsers> call = userLoginRetrofit.getServer(route);
        if(call==null)
        {
            return false;
        }
        call.enqueue(new Callback<TSysUsers>() {
            @Override
            public void onResponse(Call<TSysUsers> call, Response<TSysUsers> response) {

                TSysUsers tSysUsers=(TSysUsers)response.body();

                if (tSysUsers==null)
                {
                    UIHelper.ToastErrorMessage(mContext, "请求服务器出错！");
                    return;
                }
                else  if (tSysUsers.getStatus().equals("failure"))
                {
                    UIHelper.ToastErrorMessage(mContext, tSysUsers.getMsg());
                    return;
                }else if(tSysUsers.getStatus().equals("success")){
                    MyApplication myApplication=new MyApplication();
                    myApplication.settSysUsers(tSysUsers);
                    UIHelper.ToastGoodMessage(mContext,"登录成功！");
                    Intent intent = new Intent();
                    intent.setClass(UserLoginActivity.this, MainActivity.class);
                    startActivity(intent);


                }


            }

            @Override
            public void onFailure(Call<TSysUsers> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });

        return true;

    }
}
