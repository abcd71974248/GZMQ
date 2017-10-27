package com.hotsun.mqxxgl.busi.service;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by yuan on 2017-10-27.
 * service
 */

public interface RetrofitService {

    @POST("http://98.52.9.127:8080/mqxxgl/ldfwxxgl/ldxx/getList.do")
    Observable<String> GetUserInfo(@Query("json") String json);



}
