package com.hotsun.mqxxgl.busi.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.activity.DistrictPickerActivity;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.GetSingleDataVO;
import com.hotsun.mqxxgl.busi.service.basicplat.GetDmcodeListRetrofit;
import com.hotsun.mqxxgl.widget.OnWheelChangedListener;
import com.hotsun.mqxxgl.widget.WheelView;
import com.hotsun.mqxxgl.widget.adapters.ArrayWheelAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by netbeans on 2017/11/13.
 */

public class BmcodeUtil implements View.OnClickListener, OnWheelChangedListener {
    protected Map<String, String> bmcodeDatasMap = new HashMap<String, String>();
    protected String mCurrentName ="";
    protected String mCurrentCode ="";
    private WheelView bmWheel;
    private Button closeBtn;
    private Button saveBtn;

    private String[] mBmcodes;

    private Context context;

    private Dialog mDialog;
    private View inflate;

    private EditText dmname;
    private TextView dmcode;

    public void loadBm(Context acContext,EditText mDmname,TextView mDmcode,String bmtype){
        mDialog = new Dialog(acContext,R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(acContext).inflate(R.layout.activity_bmcode,null);
        //将布局设置给Dialog
        mDialog.setContentView(inflate);

        dmname = mDmname;
        dmcode = mDmcode;

        bmWheel = (WheelView) inflate.findViewById(R.id.bmcode);
        bmWheel.addChangingListener(this);
        bmWheel.setVisibleItems(7);

        closeBtn = (Button) inflate.findViewById(R.id.bmcode_close);
        saveBtn = (Button) inflate.findViewById(R.id.bmcode_save);

        closeBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        context = acContext;

        GetSingleDataVO getSingleDataVO = new GetSingleDataVO();
        getSingleDataVO.setSessionID(MyApplication.tSysUsers.getSessionID());
        getSingleDataVO.setUserID(MyApplication.tSysUsers.getUserID());
        getSingleDataVO.setDataid(bmtype);

        Gson gson = new Gson();
        String route = gson.toJson(getSingleDataVO);

        GetDmcodeListRetrofit getDmcodeListRetrofit = new GetDmcodeListRetrofit(acContext);
        Call<ResponseResults> call = getDmcodeListRetrofit.getServer(route);
        call.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                List<Map<String,String>> listsBmcode = response.body().getResults();
                String[] bmcodes = new String[listsBmcode.size()+1];
                for(int i=0;i < listsBmcode.size();i++){
                    bmcodes[i] = listsBmcode.get(i).get("dmname").toString();
                    bmcodeDatasMap.put(listsBmcode.get(i).get("dmname").toString(),listsBmcode.get(i).get("dmcode").toString());
                    if(i == 0){
                        mCurrentName = listsBmcode.get(i).get("dmname").toString();
                        mCurrentCode = listsBmcode.get(i).get("dmcode").toString();
                    }
                }
                bmcodes[listsBmcode.size()] = "无";
                mBmcodes = bmcodes;
                bmWheel.setViewAdapter(new ArrayWheelAdapter<String>(context, bmcodes));
                bmWheel.setCurrentItem(0);

                //获取当前Activity所在的窗体
                Window dialogWindow = mDialog.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity(Gravity.BOTTOM);
                //获得窗体的属性
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();

                lp.width=720;
                lp.height=600;
                //将属性设置给窗体
                dialogWindow.setAttributes(lp);
                mDialog.show();//显示对话框
            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        mCurrentName = mBmcodes[newValue];
        if(mCurrentName.equals("无")){
            mCurrentName = "";
            mCurrentCode = "";
        }else{
            mCurrentCode = bmcodeDatasMap.get(mCurrentName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bmcode_close:
                mDialog.dismiss();
                break;
            case R.id.bmcode_save:
                dmname.setText(mCurrentName);
                dmcode.setText(mCurrentCode);
                mDialog.dismiss();
                break;
            default:
                break;
        }
    }
}
