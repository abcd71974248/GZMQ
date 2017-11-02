package com.hotsun.mqxxgl;

import android.support.multidex.MultiDexApplication;

import com.hotsun.mqxxgl.busi.model.requestParams.UserLoginVo;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysUsers;

/**
 * Created by li on 2017/10/27.
 * 自定义Application
 */

public class MyApplication extends MultiDexApplication {



    public TSysUsers tSysUsers;

    @Override
    public void onCreate() {
        super.onCreate();

    }
    public TSysUsers gettSysUsers() {
        return tSysUsers;
    }

    public void settSysUsers(TSysUsers tSysUsers) {
        this.tSysUsers = tSysUsers;
    }


}
