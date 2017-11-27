package com.hotsun.mqxxgl.busi.service;

import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.sysbeans.AddResponseResults;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysUsers;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by yuan on 2017-10-27.
 * service
 */

public interface SyncPostRoute {

//    @Headers({ "Content-Type: application/json; charset=utf-8"})
//    @FormUrlEncoded
//    @POST("/cpimAppService/familyPopulation/family/getJthxxList.do")
//    Observable<String> GetUserInfo(@Field("userID") String userID, @Field("sessionID") String sessionID,
//                                   @Field("zuid") String zuid, @Field("page") String page
//    , @Field("hzxm") String hzxm, @Field("mph") String mph);

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/basicplat/userRight/getHomemoduleList.do")
    Call<ResponseResults> syncPostRoute(@Body RequestBody route);//传入的参数为RequestBody


    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/basicplat/userRight/getAPPModuleList.do")
    Call<ResponseResults> getModulePostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/basicplat/userRight/android_getOftenmoduleList.do")
    Call<ResponseResults> getOftenModulePostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/basicplat/userRight/android_getOftenmoduleNoLimits.do")
    Call<ResponseResults> getNoOftenModulePostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/basicplat/userRight/android_insertOftenmdl.do")
    Call<ResponseResults> addOftenModulePostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/basicplat/userRight/android_deleteOftenmdl.do")
    Call<AddResponseResults> deleteModulePostRoute(@Body RequestBody route);//传入的参数为RequestBody
}
