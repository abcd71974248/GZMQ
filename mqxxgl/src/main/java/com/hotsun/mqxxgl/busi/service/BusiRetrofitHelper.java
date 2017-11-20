package com.hotsun.mqxxgl.busi.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ConditionText;
import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysUsers;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.gis.service.LiveNetworkMonitor;
import com.hotsun.mqxxgl.gis.service.NetworkMonitor;
import com.hotsun.mqxxgl.gis.util.ToastUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by li on 2017/5/5.
 * 初始化 Retrofit
 */

public  class  BusiRetrofitHelper {

    private Context mContext;
    private static NetworkMonitor networkMonitor;
    private static BusiRetrofitHelper instance = null;
    protected Retrofit mRetrofit = null;

    public static BusiRetrofitHelper getInstance(Context context){
        if(instance == null){
            instance = new BusiRetrofitHelper(context);
            networkMonitor=new LiveNetworkMonitor(context);
        }
        return instance;
    }

    protected BusiRetrofitHelper(Context ctx){
        this.mContext = ctx;
        init();
    }

    private void init(){
        resetApp();
    }

    private void resetApp() {
        SharedPreferences autoPreferences = mContext.getSharedPreferences("ipsetinfo",
                mContext.MODE_PRIVATE);
        String ip=autoPreferences.getString("ip","");
        String port=autoPreferences.getString("port","");

        String baseUrl="http://"+ip+":"+port+"/";
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        // 看这里 ！！！ 我们添加了一个网络监听拦截器
        /*okHttpClientBuilder.addInterceptor(chain -> {
            boolean connected = networkMonitor.isConnected();
            if (networkMonitor.isConnected()) {
                return chain.proceed(chain.request());
            } else {
                throw new NoNetworkException();
            }
        });*/
        okHttpClientBuilder.addNetworkInterceptor(new MyNetworkInterceptor());
        okHttpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClientBuilder.build())
                .addConverterFactory( GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
     }

//    public  Call<ResponseResults> getServer(String route){

//        PostRoute postRoute=mRetrofit.create(PostRoute.class);
//        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
//        Call<ResponseResults> call=postRoute.postFlyRoute(body);
//        return null;
//    }

    private class MyNetworkInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            if (networkMonitor.isConnected()) {
                return chain.proceed(chain.request());
            } else {
                ToastUtil.setToast(mContext,"无网络连接，请检查网络");
            }
            return null;
        }
    }
}
