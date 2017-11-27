package com.hotsun.mqxxgl;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.hotsun.mqxxgl.busi.model.requestParams.UserLoginVo;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysUsers;
import com.hotsun.mqxxgl.common.ScreenTool;
import com.hotsun.mqxxgl.gis.util.ResourcesUtil;
import com.hotsun.mqxxgl.permissions.PermissionsActivity;
import com.hotsun.mqxxgl.permissions.PermissionsChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by li on 2017/10/27.
 * 自定义Application
 */

public class MyApplication extends MultiDexApplication {

    public static MyApplication mApplication;

    public static ScreenTool.Screen screen;

    public static TSysUsers tSysUsers=null;

    public static MyApplication getInstance(){
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        screen = ScreenTool.getScreenPix(this);
        /** 注册网络监听 */
        //initNetworkReceiver();
        mApplication = this;

    }
    public TSysUsers gettSysUsers() {
        return tSysUsers;
    }

    public void settSysUsers(TSysUsers tSysUsers) {
        this.tSysUsers = tSysUsers;
    }


}
