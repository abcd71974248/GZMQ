package com.hotsun.mqxxgl.busi.service;

import android.content.Context;

import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.util.UIHelper;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by yuan on 2017-11-01.
 */

public class GetOftenModuleRetrofit extends BusiRetrofitHelper{


    private Context mContext;
    public GetOftenModuleRetrofit(Context ctx) {
        super(ctx);
        this.mContext=ctx;
        BusiRetrofitHelper.getInstance(ctx);
    }

    public Call<ResponseResults> getServer(String route){

        if (!UIHelper.isNetConntion(mContext))
        {
            return null;
        }
        SyncPostRoute syncPostRoute=mRetrofit.create(SyncPostRoute.class);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        Call<ResponseResults> call=syncPostRoute.getOftenModulePostRoute(body);
        return call;
    }

}
