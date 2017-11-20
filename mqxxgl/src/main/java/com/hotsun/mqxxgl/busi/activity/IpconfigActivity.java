package com.hotsun.mqxxgl.busi.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.DeviceUuidFactory;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.UserLoginVo;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysUsers;
import com.hotsun.mqxxgl.busi.service.UserLoginRetrofit;
import com.hotsun.mqxxgl.busi.service.basicplat.IPConfigRetrofit;
import com.hotsun.mqxxgl.busi.util.MD5Utils;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.gis.activity.GisBaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IpconfigActivity extends AppCompatActivity  implements  View.OnClickListener {

    private Context mContext;
    private EditText edtIpaddr, edtIpport;
    private boolean  cansave=false;
    private Button setipconfig,clientipconfig;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipconfig);


        mContext = IpconfigActivity.this;
        edtIpaddr = (EditText) findViewById(R.id.edtTxt_ipconfig_ipaddr);
        edtIpport = (EditText) findViewById(R.id.edtTxt_ipconfig_ipport);

        TextView txtTitle=(TextView)findViewById(R.id.text_title) ;
        txtTitle.setText("设置服务器连接信息");
        SharedPreferences ipinfoPreferences = getSharedPreferences("ipsetinfo",
                mContext.MODE_PRIVATE);
        String ipaddr=ipinfoPreferences.getString("ip","");
        String portaddr=ipinfoPreferences.getString("port","");
        if (!ipaddr.equals("")&&!portaddr.equals(""))
        {

            edtIpaddr.setText(ipaddr);
            edtIpport.setText(portaddr);

        }


        TextView left_bar = (TextView) findViewById(R.id.left_bar);
        left_bar.setOnClickListener(this);

        setipconfig= (Button) findViewById(R.id.btn_save_setipconfig);
        setipconfig.setOnClickListener(this);
        clientipconfig= (Button) findViewById(R.id.btn_save_clientipconfig);
        clientipconfig.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_bar:
                finish();
                break;
            case R.id.btn_save_setipconfig:
                dialog = ProgressDialog.show(this, "提示", "正在检测连接",  false, true);
                setipconfig();
                break;
            case R.id.btn_save_clientipconfig:
                clientipconfig();
                break;
        }
    }

    private boolean setipconfig() {
        String ipaddr = edtIpaddr.getText().toString();
        String ipport = edtIpport.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("ipsetinfo", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("ip",ipaddr);
        editor.putString("port",ipport);
        editor.commit();//提交修改
        if (checkIP(ipaddr.trim())) {

            IPConfigRetrofit ipConfigRetrofit=new IPConfigRetrofit(IpconfigActivity.this);
            Call<ResponseResults> call = ipConfigRetrofit.getServer();
            if(call==null)
            {
                return false;
            }
            call.enqueue(new Callback<ResponseResults>() {
                @Override
                public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                    ResponseResults responseResults=(ResponseResults)response.body();

                    if (responseResults==null)
                    {
                        cansave=false;
                        dialog.dismiss();
                        UIHelper.ToastErrorMessage(mContext, "请求服务器出错！");
                        return;
                    }

                    if (responseResults.getStatus().equals("success"))
                    {
                       cansave=true;
                        dialog.dismiss();
                        UIHelper.ToastGoodMessage(mContext, "测试通过！");

                    }else {


                        cansave=false;
                        dialog.dismiss();
                        UIHelper.ToastErrorMessage(mContext, "IP和端口与服务器的端口有误！");

                        return;


                    }

                }

                @Override
                public void onFailure(Call<ResponseResults> call, Throwable t) {
                    cansave=false;
                    dialog.dismiss();
                    UIHelper.ToastErrorMessage(mContext, "IP和端口与服务器的端口有误！");
                    return;
                }
            });

        }

        return true;

    }
    private boolean clientipconfig() {

        if (cansave) {
            IPConfigRetrofit ipConfigRetrofit=new IPConfigRetrofit(IpconfigActivity.this);
            Call<ResponseResults> call = ipConfigRetrofit.getServer();
            if(call==null)
            {
                return false;
            }
            call.enqueue(new Callback<ResponseResults>() {
                @Override
                public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                    ResponseResults responseResults=(ResponseResults)response.body();

                    if (responseResults==null)
                    {
                        UIHelper.ToastErrorMessage(mContext, "请求服务器出错,请检查IP和端口是否正确！");
                        return;
                    }

                    if (responseResults.getStatus().equals("success"))
                    {
                        SharedPreferences sharedPreferences = getSharedPreferences("ipsetinfo", mContext.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString("ip",edtIpaddr.getText().toString());
                        editor.putString("port",edtIpport.getText().toString());
                        editor.commit();//提交修改
                        UIHelper.ToastGoodMessage(mContext, "保存成功！");
                        finish();
                    }else {


                        UIHelper.ToastErrorMessage(mContext, "IP和端口与服务器的端口有误！");

                        return;


                    }

                }

                @Override
                public void onFailure(Call<ResponseResults> call, Throwable t) {
                    UIHelper.ToastErrorMessage(mContext, "请求服务器出错,请检查IP和端口是否正确！");
                    return;

                }
            });

        }else
        {
            UIHelper.ToastErrorMessage(mContext,"请先设置正确的服务器连接信息并确保测试连接成功！");
            return false;
        }



        return true;

    }

    //验证IP是否正确
    public boolean checkIP(String strIP){
        String strPattern = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strIP);
        boolean isValid = m.matches();
        if(!isValid){
            edtIpaddr.setText("");
            UIHelper.ToastErrorMessage(mContext,"您输入的IP地址不合法！");
        }
        return isValid;
    }

}
