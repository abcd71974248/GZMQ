package com.hotsun.mqxxgl.busi.service;

import com.hotsun.mqxxgl.busi.model.ConditionText;
import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.sysbeans.AddResponseResults;
import com.hotsun.mqxxgl.busi.model.sysbeans.TSysUsers;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by yuan on 2017-10-27.
 * service
 */

public interface PostRoute {

//    @Headers({ "Content-Type: application/json; charset=utf-8"})
//    @FormUrlEncoded
//    @POST("/cpimAppService/familyPopulation/family/getJthxxList.do")
//    Observable<String> GetUserInfo(@Field("userID") String userID, @Field("sessionID") String sessionID,
//                                   @Field("zuid") String zuid, @Field("page") String page
//    , @Field("hzxm") String hzxm, @Field("mph") String mph);

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/familyPopulation/family/getJthxxList.do")
    Call<ResponseResults> postFlyRoute(@Body RequestBody route);//传入的参数为RequestBody


    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/android_login.do")
    Call<TSysUsers> userLoginPostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/android_xzbmQuery/getCunbm.do")
    Call<ResponseResults> getCunbmPostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/android_xzbmQuery/getZubm.do")
    Call<ResponseResults> getZubmPostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/ldfwxxgl/ldxx/android_getList.do")
    Call<ResponseResults> getLdxxPostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/ldfwxxgl/ldxx/android_getLdxxByPrimarykey.do")
    Call<ResponseResults> getLdxxViewPostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/ldfwxxgl/ldxx/android_add.do")
    Call<ResponseResults> addLdPostRoute(@Body RequestBody route);//传入的参数为RequestBody

    @Headers({ "Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @POST("/cpimAppService/ldfwxxgl/ldxx/android_deleteLdxx.do")
    Call<AddResponseResults> deleteLdxx(@Body RequestBody route);//传入的参数为RequestBody
}
