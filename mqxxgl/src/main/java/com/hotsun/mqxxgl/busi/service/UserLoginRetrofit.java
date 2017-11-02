package com.hotsun.mqxxgl.busi.service;

import android.content.Context;

import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysUsers;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by yuan on 2017-11-01.
 */

public class UserLoginRetrofit extends BusiRetrofitHelper{


    public UserLoginRetrofit(Context ctx) {
        super(ctx);
        BusiRetrofitHelper.getInstance(ctx);
    }

    public Call<TSysUsers> getServer(String route){

        PostRoute postRoute=mRetrofit.create(PostRoute.class);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        Call<TSysUsers> call=postRoute.userLoginPostRoute(body);
        return call;
    }

}
