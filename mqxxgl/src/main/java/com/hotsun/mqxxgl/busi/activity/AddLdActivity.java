package com.hotsun.mqxxgl.busi.activity;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddLdActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;

    private TextView xjrqDialog;
    private TextView zxrqDialog;
    private Calendar cal;
    private int year,month,day;

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

        getDate();

        xjrqDialog=(TextView) findViewById(R.id.addld_xjrqtext);
        xjrqDialog.setOnClickListener(this);
        zxrqDialog=(TextView) findViewById(R.id.addld_zxrqtext);
        zxrqDialog.setOnClickListener(this);
    }

    //获取当前日期
    private void getDate() {
        cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
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
        String xjrq = String.valueOf(xjrqText.getText());

        EditText bzText = (EditText) findViewById(R.id.addld_bztext);
        String bz = String.valueOf(bzText.getText());

        EditText zxlxText = (EditText) findViewById(R.id.addld_zxlxtext);
        String zxlx = String.valueOf(zxlxText.getText());

        EditText zxrqText = (EditText) findViewById(R.id.addld_zxrqtext);
        String zxrq = String.valueOf(zxrqText.getText());


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

            case R.id.addld_xjrqtext:
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        xjrqDialog.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(AddLdActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;

            case R.id.addld_zxrqtext:
                listener=new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        zxrqDialog.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                dialog=new DatePickerDialog(AddLdActivity.this, 0,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;

            default:
                break;
        }



    }
}
