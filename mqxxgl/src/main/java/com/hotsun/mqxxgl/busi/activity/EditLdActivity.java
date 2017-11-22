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
import com.hotsun.mqxxgl.busi.model.requestParams.GetSingleDataVO;
import com.hotsun.mqxxgl.busi.service.ldxxgl.AddLdRetrofit;
import com.hotsun.mqxxgl.busi.service.ldxxgl.EditLdRetrofit;
import com.hotsun.mqxxgl.busi.service.ldxxgl.GetLdxxViewRetrofit;
import com.hotsun.mqxxgl.busi.util.BmcodeUtil;
import com.hotsun.mqxxgl.busi.util.UIHelper;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditLdActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;

    private TextView xjrqDialog;
    private TextView zxrqDialog;
    private Calendar cal;
    private int year,month,day;

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

    TextView fwjgdm;
    TextView zxlxdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editld);

        TextView txtTitle= (TextView)findViewById(R.id.text_title);
        txtTitle.setText("修改楼栋信息");
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

        fwjgdm = (TextView) findViewById(R.id.editld_fwjgdm);
        zxlxdm = (TextView) findViewById(R.id.editld_zxlxdm);

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

        getDate();

        xjrqDialog=(TextView) findViewById(R.id.editld_xjrqtext);
        xjrqDialog.setOnClickListener(this);
        findViewById(R.id.editld_xjrqclear).setOnClickListener(this);
        zxrqDialog=(TextView) findViewById(R.id.editld_zxrqtext);
        zxrqDialog.setOnClickListener(this);
        findViewById(R.id.editld_zxrqclear).setOnClickListener(this);

        fwjgText.setOnClickListener(this);
        zxlxText.setOnClickListener(this);
    }

    //获取当前日期
    private void getDate() {
        cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
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

                fwjgdm.setText(results.get(0).get("fwjgdm"));
                zxlxdm.setText(results.get(0).get("zxlxdm"));
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

        TextView fwjgText = (TextView) findViewById(R.id.editld_fwjgdm);
        String fwjg = String.valueOf(fwjgText.getText());

        EditText xjrqText = (EditText) findViewById(R.id.editld_xjrqtext);
        String xjrq = String.valueOf(xjrqText.getText());

        EditText bzText = (EditText) findViewById(R.id.editld_bztext);
        String bz = String.valueOf(bzText.getText());

        TextView zxlxText = (TextView) findViewById(R.id.editld_zxlxdm);
        String zxlx = String.valueOf(zxlxText.getText());

        EditText zxrqText = (EditText) findViewById(R.id.editld_zxrqtext);
        String zxrq = String.valueOf(zxrqText.getText());

        if(ldmc.length() == 0){
            Toast.makeText(EditLdActivity.this, "楼栋名称不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(lddz.length() == 0){
            Toast.makeText(EditLdActivity.this, "楼栋地址不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(dys.length() == 0){
            Toast.makeText(EditLdActivity.this, "单元数不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(lcs.length() == 0){
            Toast.makeText(EditLdActivity.this, "楼层数不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }


        FwLdxx fwLdxx = new FwLdxx();

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
                        setResult(2);
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
            case R.id.editld_xjrqtext:
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        xjrqDialog.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(EditLdActivity.this,R.style.ThemeDialog,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;

            case R.id.editld_zxrqtext:
                listener=new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        zxrqDialog.setText(year+"-"+(++month)+"-"+day);      //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                    }
                };
                dialog=new DatePickerDialog(EditLdActivity.this, R.style.ThemeDialog,listener,year,month,day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();
                break;
            case R.id.editld_fwjgtext:
                BmcodeUtil bmcodeUtil = new BmcodeUtil();
                EditText dmname = (EditText) findViewById(R.id.editld_fwjgtext);
                TextView dmcode = (TextView) findViewById(R.id.editld_fwjgdm);
                bmcodeUtil.loadBm(mContext,dmname,dmcode,"3007");
                break;
            case R.id.editld_zxlxtext:
                bmcodeUtil = new BmcodeUtil();
                dmname = (EditText) findViewById(R.id.editld_zxlxtext);
                dmcode = (TextView) findViewById(R.id.editld_zxlxdm);
                bmcodeUtil.loadBm(mContext,dmname,dmcode,"3017");
                break;
            case R.id.editld_xjrqclear:
                xjrqDialog.setText("");
                break;
            case R.id.editld_zxrqclear:
                zxrqDialog.setText("");
                break;
            default:
                break;

        }

    }
}
