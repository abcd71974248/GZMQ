package com.hotsun.mqxxgl.busi.service.basicplat;

import android.content.Context;

import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.service.BusiRetrofitHelper;
import com.hotsun.mqxxgl.busi.service.PostRoute;
import com.hotsun.mqxxgl.busi.util.UIHelper;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by yuan on 2017-11-01.
 */

public class IPConfigRetrofit extends BusiRetrofitHelper{

    private Context mContext;

    public IPConfigRetrofit(Context ctx) {
        super(ctx);
        this.mContext=ctx;
        BusiRetrofitHelper.getInstance(ctx);
    }

    public Call<ResponseResults> getServer(){

        if (!UIHelper.isNetConntion(mContext))
        {
            return null;
        }
        PostRoute postRoute=mRetrofit.create(PostRoute.class);

        Call<ResponseResults> call=postRoute.ipconfig();
        return call;
    }

}
