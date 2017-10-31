package com.hotsun.mqxxgl.busi.service;

import com.hotsun.mqxxgl.busi.model.ConditionText;
import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.busi.model.ResponseResults;

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


}
