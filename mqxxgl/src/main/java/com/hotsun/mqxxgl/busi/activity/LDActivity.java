package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.GetLdxxVO;
import com.hotsun.mqxxgl.busi.presenter.MyLdListAdapter;
import com.hotsun.mqxxgl.busi.service.GetLdxxListRetrofit;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.busi.view.RefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class LDActivity extends AppCompatActivity implements RefreshListView.OnRefreshListener ,RefreshListView.OnPaginationListener {

    private Context mContext;

    List<Map<String, String>> results=new ArrayList<Map<String, String>>();
    private RefreshListView refreshLv;
    private MyLdListAdapter myLdListAdapter;
    private int requestCode = 1;
    private final static int REQUESTCODE = 1; // 返回的结果码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldlist);
        mContext = LDActivity.this;

        String mid = getIntent().getStringExtra("mid");

        refreshLv = (RefreshListView) findViewById(R.id.refresh_lv);
        TextView txtTitle=(TextView)findViewById(R.id.text_title) ;
        txtTitle.setText("楼栋清单");

        refreshLv.setOnRefreshListener(this);
        refreshLv.setOnPaginationListener(this);



        Button qycxButton = (Button) findViewById(R.id.qycxbutton);
        qycxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qycxIntent = new Intent(LDActivity.this,DistrictPickerActivity.class);
                startActivityForResult(qycxIntent,REQUESTCODE);
            }
        });
        TextView qycxButton_onClick = (TextView) findViewById(R.id.qycxbutton_onclick);
        qycxButton_onClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLdList(0);
            }
        });

        LinearLayout addldButton = (LinearLayout) findViewById(R.id.busi_add_ldxx);
        addldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLd();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {
           if (requestCode == REQUESTCODE) {
                    String mCurrentDistrictName = data.getStringExtra("mCurrentDistrictName");
                    String mCurrentZipCode = data.getStringExtra("mCurrentZipCode");
                    String mCurrentCityName = data.getStringExtra("mCurrentCityName");
                   //设置结果显示框的显示数值
                   Button qycxButton = (Button) findViewById(R.id.qycxbutton);
                   qycxButton.setText(mCurrentDistrictName);
                   TextView txtldqycxx_zcode=(TextView) findViewById(R.id.ldqycxx_zcode);
                   txtldqycxx_zcode.setText(mCurrentZipCode);
                   TextView ldqycxCunmc=(TextView) findViewById(R.id.ldqycx_cunmc);
                   ldqycxCunmc.setText(mCurrentCityName);
                }
        }


    }

    private boolean getLdList(final int page) {

        String userID= MyApplication.tSysUsers.getUserID();
        String sessionID=MyApplication.tSysUsers.getSessionID();

        Gson gson = new Gson();
        GetLdxxVO getLdxxVO = new GetLdxxVO();
        getLdxxVO.setPage(page);

        getLdxxVO.setUserID(userID);
        getLdxxVO.setSessionID(sessionID);
        TextView txt_zuid=(TextView)findViewById(R.id.ldqycxx_zcode);

        String zuid= String.valueOf(txt_zuid.getText());

        getLdxxVO.setZuid(zuid);

        getLdxxVO.setLdmc("");
        String route = gson.toJson(getLdxxVO);

        GetLdxxListRetrofit getLdxxListRetrofit=new GetLdxxListRetrofit(LDActivity.this);

        Call<ResponseResults> call = getLdxxListRetrofit.getServer(route);
        call.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                ResponseResults responseResults=(ResponseResults)response.body();
                if (responseResults.getStatus().equals("failure"))
                {
                    UIHelper.ToastErrorMessage(mContext, "请求服务器出错！");
                    return;
                }

                List<Map<String, String>> newResultList=responseResults.getResults();


                if (results.size()>0)

                {
                    if(newResultList.size()==0)
                    {
                        UIHelper.ToastMessage(mContext,"没有更多的数据了！");
                        return;
                    }
                    for (int i=0;i<newResultList.size();i++)
                    {
                        results.add(newResultList.get(i));
                    }

                }

                if(page==0)
                {
                    results=newResultList;
                }

                myLdListAdapter = new MyLdListAdapter(mContext,results);
                refreshLv.setAdapter(myLdListAdapter);



                refreshLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Map<String,String> ldlistResult= (Map<String,String>)parent.getAdapter().getItem(position);
                        String ldid=ldlistResult.get("ldid");
                        Intent intent = new Intent();
                        intent.putExtra("ldid", ldid);

                        intent.setClass(LDActivity.this, LdViewActivity.class);

                        startActivity(intent);





                    }

                });


            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });

        return true;

    }

    private void addLd(){
        TextView qycxCunmc = (TextView) findViewById(R.id.ldqycx_cunmc);
        Button qycxZumc = (Button) findViewById(R.id.qycxbutton);
        TextView qycxZuid = (TextView) findViewById(R.id.ldqycxx_zcode);
        String cunmc = String.valueOf(qycxCunmc.getText());
        String zumc = String.valueOf(qycxZumc.getText());
        String zuid = String.valueOf(qycxZuid.getText());

        if(("区域选择").equals(zumc)){
            Toast.makeText(LDActivity.this, "请先选择到组", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(LDActivity.this,AddLdActivity.class);
        intent.putExtra("cunmc",cunmc);
        intent.putExtra("zumc",zumc);
        intent.putExtra("zuid",zuid);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {

        getLdList(0);
        refreshLv.refreshComplete();

    }

    @Override
    public void onPagination() {

        if (results==null)
        {
            UIHelper.ToastErrorMessage(mContext,"请先查询数据，才能加载更多数据！");
            return;
        }
        int listCount=results.size();
        if (listCount<10)
        {
            LinearLayout footpage=(LinearLayout)findViewById(R.id.page_footer) ;
            footpage.setVisibility(View.GONE);
        }
//        if(listCount==10)
//        {
//            LinearLayout footpage=(LinearLayout)findViewById(R.id.page_footer) ;
//            footpage.setVisibility(View.VISIBLE);
//            TextView textView=(TextView)findViewById(R.id.page_footer_tv);
//            textView.setText("没有更多的数据了");
//        }
        int page= (int) Math.floor(listCount/10);
        getLdList(page);
        refreshLv.refreshComplete();

    }
}
