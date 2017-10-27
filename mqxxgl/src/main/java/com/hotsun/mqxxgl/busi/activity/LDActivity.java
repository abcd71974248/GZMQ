package com.hotsun.mqxxgl.busi.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.SubMenuBuilder;
import android.util.Log;


import com.google.gson.Gson;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ConditionText;
import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.gis.service.RetrofitHelper;


import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LDActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldlist);
        getUserinfo();


    }


    private  void getUserinfo(){
        Gson gson = new Gson();
        String page="12";
        String start="1";
        String limit="12";
        String conditionText="";

        Log.d("mess","进来了");
        ConditionText condition=new ConditionText();
        condition.setPage(page);
        condition.setStart(start);
        condition.setLimit(limit);
        condition.setConditionText(conditionText);

        String json = gson.toJson(condition);
        Observable<String> observer = RetrofitHelper.getInstance(LDActivity.this).getServer().GetUserInfo(json);
        observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {



                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });

    }

}
