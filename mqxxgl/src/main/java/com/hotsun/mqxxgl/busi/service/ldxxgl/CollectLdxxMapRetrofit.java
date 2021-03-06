package com.hotsun.mqxxgl.busi.service.ldxxgl;

import android.content.Context;

import com.hotsun.mqxxgl.busi.model.sysbeans.AddResponseResults;
import com.hotsun.mqxxgl.busi.service.BusiRetrofitHelper;
import com.hotsun.mqxxgl.busi.service.PostRoute;
import com.hotsun.mqxxgl.busi.util.UIHelper;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by yuan on 2017-11-01.
 */

public class CollectLdxxMapRetrofit extends BusiRetrofitHelper{

    private Context mContext;

    public CollectLdxxMapRetrofit(Context ctx) {
        super(ctx);
        this.mContext=ctx;
        BusiRetrofitHelper.getInstance(ctx);
    }

    public Call<AddResponseResults> getServer(String route){

        if (!UIHelper.isNetConntion(mContext))
        {
            return null;
        }
        PostRoute postRoute=mRetrofit.create(PostRoute.class);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        Call<AddResponseResults> call=postRoute.collectLdxxMap(body);
        return call;
    }

}
