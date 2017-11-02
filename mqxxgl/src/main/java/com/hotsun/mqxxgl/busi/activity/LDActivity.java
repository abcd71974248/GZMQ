package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.SubMenuBuilder;
import android.util.Log;


import com.google.gson.Gson;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ConditionText;
import com.hotsun.mqxxgl.busi.model.FwLdxx;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.service.BusiRetrofitHelper;
import com.hotsun.mqxxgl.gis.service.RetrofitHelper;
import com.hotsun.mqxxgl.gis.util.ToastUtil;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LDActivity extends AppCompatActivity  {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldlist);
        mContext = LDActivity.this;

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
        condition.setHzxm("");
        condition.setMph("");
        condition.setSessionID("864906032912696,864906032989942|20171027172938819");
        condition.setUserID("35");
        condition.setZuid("520300000000017202");

        String route = gson.toJson(condition);

        Map<String,Object> res=new HashMap<String, Object>();
        res.put("userID",35);
        res.put("sessionID","864906032912696,864906032989942|20171027172938819");
        res.put("zuid","520300000000017202");
        res.put("page",1);
        res.put("hzxm","");
        res.put("mph","");

//        Observable<String> observer = BusiRetrofitHelper.getInstance(LDActivity.this).getServer().GetUserInfo(condition.getUserID(),condition.getSessionID(), condition.getZuid(),
//                condition.getPage(),condition.getHzxm(),condition.getMph());

//        Call<ResponseResults> call = BusiRetrofitHelper.getInstance(LDActivity.this).getServer(route);
//        call.enqueue(new Callback<ResponseResults>() {
//            @Override
//            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {
//
//                Log.i("sssss",response.body().toString());
//                List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
//                    for(AppInfo app : allNews){
//                          HashMap<String, Object> item = new HashMap<String, Object>();
//                         item.put("name", app.getName());
//                          item.put("count", app.getCount());
//                        data.add(item);
//                  }
//                 //创建SimpleAdapter适配器将数据绑定到item显示控件上
//                  SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
//                           new String[]{"name", "count"}, new int[]{R.id.name, R.id.count});
//                  //实现列表的显示
//                  listView.setAdapter(adapter);
//                  //条目点击事件
//                 listView.setOnItemClickListener(new ItemClickListener());


//        }

//            @Override
//            public void onFailure(Call<ResponseResults> call, Throwable t) {
//                Log.i("sssss",t.getMessage());
//            }
//        });
//        Log.i("postjson", route);


    }

}
