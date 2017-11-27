package com.hotsun.mqxxgl.busi.service;

import android.content.Context;

import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.sysbeans.AddResponseResults;
import com.hotsun.mqxxgl.busi.util.UIHelper;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by yuan on 2017-11-01.
 */

public class DeleteMdlxxRetrofit extends BusiRetrofitHelper{

    private Context mContext;

    public DeleteMdlxxRetrofit(Context ctx) {
        super(ctx);
        this.mContext=ctx;
        BusiRetrofitHelper.getInstance(ctx);
    }

    public Call<AddResponseResults> getServer(String route){

        if (!UIHelper.isNetConntion(mContext))
        {
            return null;
        }
        SyncPostRoute syncPostRoute=mRetrofit.create(SyncPostRoute.class);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        Call<AddResponseResults> call=syncPostRoute.deleteModulePostRoute(body);
        return call;
    }

}
