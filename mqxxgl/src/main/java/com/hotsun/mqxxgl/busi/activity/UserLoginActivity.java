package com.hotsun.mqxxgl.busi.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.hotsun.mqxxgl.busi.util.Hotsun;
import com.hotsun.mqxxgl.busi.util.MD5Utils;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.gis.service.LiveNetworkMonitor;
import com.hotsun.mqxxgl.gis.service.NetworkMonitor;
import com.hotsun.mqxxgl.gis.util.ResourcesUtil;
import com.hotsun.mqxxgl.permissions.PermissionsActivity;
import com.hotsun.mqxxgl.permissions.PermissionsChecker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class UserLoginActivity extends AppCompatActivity  implements  View.OnClickListener {

    private Context mContext;
    private EditText edtUserbh, edtPassword;
    private Switch  autoLogin;

    /*动态检测权限*/
    private static final int REQUEST_CODE = 10000; // 权限请求码
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

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
        TextView ipconfig=(TextView)findViewById(R.id.tv_set_ipconfig);
        ipconfig.setOnClickListener(this);


        autoLogin=(Switch)findViewById(R.id.btn_userlogin_switch);

        SharedPreferences autoPreferences = getSharedPreferences("autoUserInfo",
                mContext.MODE_PRIVATE);
        String userNumber=autoPreferences.getString("userNumber","");
        String password=autoPreferences.getString("password","");
        if (!userNumber.equals("")&&!password.equals(""))
        {

            edtUserbh.setText(userNumber);
            edtPassword.setText(password);
            autoLogin.setChecked(true);

            userLogin();
        }

        mPermissionsChecker = new PermissionsChecker(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            ActivityCompat.requestPermissions(this,PERMISSIONS,REQUEST_CODE);
        }else{
            ResourcesUtil.getInstance().creatFolder(this);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if(isAllGranted){
                ResourcesUtil.getInstance().creatFolder(this);
//                copyAssets();
            }
        }

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_set_ipconfig:
                Intent intent = new Intent();
                intent.setClass(UserLoginActivity.this, IpconfigActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_userlogin_submit:
                userLogin();
                break;
        }
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

        SharedPreferences autoPreferences = mContext.getSharedPreferences("ipsetinfo",
                mContext.MODE_PRIVATE);
        String ip=autoPreferences.getString("ip","");
        String port=autoPreferences.getString("port","");
        if (ip.equals("")&&port.equals(""))
        {
            UIHelper.ToastErrorMessage(mContext,"请设置IP和端口后再操作！");
            return false;

        }

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


                    SharedPreferences sharedPreferences = getSharedPreferences("userInfo", mContext.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putString("userID",tSysUsers.getUserID());
                    editor.putString("xzName",tSysUsers.getXzName());
                    editor.putString("zuName",tSysUsers.getZuName());
                    editor.putString("sessionID",tSysUsers.getSessionID());
                    editor.putString("status",tSysUsers.getStatus());
                    editor.putString("xzCodeid",tSysUsers.getXzCodeid());
                    editor.putString("cunID",tSysUsers.getCunID());
                    editor.putString("cunName",tSysUsers.getCunName());
                    editor.putString("userName",tSysUsers.getUserName());
                    editor.putString("zuID",tSysUsers.getZuID());
                    editor.putString("roleName",tSysUsers.getRoleName());
                    editor.putString("xzCode",tSysUsers.getXzCode());
                    editor.putString("msg",tSysUsers.getMsg());

                    editor.commit();//提交修改


//                    SharedPreferences autoLoginPre = getSharedPreferences("autoUserInfo",
//                            mContext.MODE_PRIVATE);
//                    SharedPreferences.Editor autoEditor = autoLoginPre.edit();//获取编辑器
//                    autoEditor.clear();
//                    autoEditor.commit();
                    if(autoLogin.isChecked())
                    {
                        SharedPreferences autoLoginPre = getSharedPreferences("autoUserInfo",
                                mContext.MODE_PRIVATE);
                        SharedPreferences.Editor autoEditor = autoLoginPre.edit();//获取编辑器
                        autoEditor.putString("userNumber",edtUserbh.getText().toString());
                        autoEditor.putString("password",edtPassword.getText().toString());
                        autoEditor.commit();
                    }

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
                UIHelper.ToastErrorMessage(mContext, "请求服务器出错,请检查IP和端口是否正确！");
                return;
            }
        });

        return true;

    }
}
