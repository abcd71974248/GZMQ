package com.hotsun.mqxxgl.busi.service.ldxxgl;

import android.content.Context;

import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.service.BusiRetrofitHelper;
import com.hotsun.mqxxgl.busi.service.PostRoute;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by yuan on 2017-11-01.
 */

public class AddLdRetrofit extends BusiRetrofitHelper{


    public AddLdRetrofit(Context ctx) {
        super(ctx);
        BusiRetrofitHelper.getInstance(ctx);
    }

    public Call<ResponseResults> addLd(String route){

        PostRoute postRoute=mRetrofit.create(PostRoute.class);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        Call<ResponseResults> call=postRoute.addLdPostRoute(body);
        return call;
    }

}
