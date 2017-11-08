package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.HomemoduleVO;
import com.hotsun.mqxxgl.busi.presenter.MyGridAdapter;
import com.hotsun.mqxxgl.busi.service.HomemoduleRetrofit;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.busi.view.MyGridView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private MyGridView gridview;
    private Context mContext;
    public int[] imgs = { R.string.jtda_icon, R.string.ldda_icon,
            R.string.fwda_icon, R.string.qygk_icon,R.string.pjjy_icon,R.string.ldjy_icon,R.string.shbz_icon,
            R.string.jhsy_icon,R.string.cyfw_icon,R.string.zzgl_icon};
    public String[] sysids = { "100100", "100200", "100300", "100400", "100500", "100600",
            "100700", "100800", "100900", "101000"};

    public String[] img_text = { "家庭档案", "楼栋档案", "房屋档案", "区域概况", "普及教育", "劳动就业",
            "社会保障", "计划生育", "村医服务", "综治管理"};


    public int[] imgColor = { R.color.jtda_color, R.color.ldda_color,
            R.color.fwda_color, R.color.qygk_color,R.color.pjjy_color,R.color.ldjy_color,R.color.shbz_color,
            R.color.jhsy_color,R.color.cyfw_color,R.color.zzgl_color};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        gridview=(MyGridView) findViewById(R.id.gridview);
        getHomemodule();

    }

    private void initView() {

        gridview.setAdapter(new MyGridAdapter(this,imgs,img_text,imgColor));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.jtrkgl_icon, R.string.cjrq_icon,
                            R.string.kclr_icon, R.string.lset_icon};

                    String[] module_img_text = { "家庭人口", "残疾人群", "空巢老人", "留守儿童"};
                    String[] moduleids = { "10010001", "10010002", "10010003", "10010004"};

                    int[] module_imgColor = { R.color.jtrkgl_color, R.color.cjrq_color,
                            R.color.kclr_color, R.color.lset_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,ModulepageActivity.class);
                    startActivity(intent);
                }
               else if (position == 1) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.ldxxgl_icon, R.string.ldssgl_icon};

                    String[] module_img_text = { "楼栋信息", "楼栋设施"};
                    String[] moduleids = { "10020001", "10020002"};

                    int[] module_imgColor = { R.color.ldxxgl_color, R.color.ldssgl_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,
                            ModulepageActivity.class);
                    startActivity(intent);
                }
               else   if (position == 2) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.fwxxgl_icon, R.string.shhjgl_icon};

                    String[] module_img_text = { "房屋信息", "生活环境"};
                    String[] moduleids = { "10030001", "10030002"};

                    int[] module_imgColor = { R.color.fwxxgl_color, R.color.shhjgl_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,
                            ModulepageActivity.class);
                    startActivity(intent);
                }
                else   if (position == 3) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.qygk_jbxx_icon, R.string.qygk_fwry_icon,R.string.qygk_xxxx_icon, R.string.qygk_rkqk_icon,
                            R.string.qygk_sdxx_icon, R.string.qygk_cgxx_icon,R.string.qygk_jtjj_icon, R.string.qygk_tsfw_icon};

                    String[] module_img_text = {"基本信息","服务人员", "学校信息", "人口情况","商店信息","餐馆信息","集体经济", "特色产品"};
                    String[] moduleids = { "10040001", "10040002","10040003","10040004", "10040005","10040006", "10040007", "10040008"};

                    int[] module_imgColor = { R.color.qygk_jbxx_color, R.color.qygk_fwry_color,R.color.qygk_xxxx_color, R.color.qygk_rkqk_color,
                            R.color.qygk_sdxx_color, R.color.qygk_cgxx_color,R.color.qygk_jtjj_color, R.color.qygk_tsfw_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,
                            ModulepageActivity.class);
                    startActivity(intent);
                }
                else   if (position == 4) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.pjjy_snrq_icon, R.string.pjjy_jycd_icon,R.string.pjjy_pkxs_icon};

                    String[] module_img_text = {"适龄人群","教育程度", "贫困学生"};
                    String[] moduleids = { "10050001", "10050002","10050003"};

                    int[] module_imgColor = { R.color.pjjy_snrq_color, R.color.pjjy_jycd_color,R.color.pjjy_pkxs_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,
                            ModulepageActivity.class);
                    startActivity(intent);
                }
                else   if (position == 5) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.ldjy_jyzk_icon, R.string.ldjy_ssll_icon,R.string.ldjy_wcwg_icon,
                            R.string.ldjy_ldjn_icon, R.string.ldjy_qzxq_icon,R.string.ldjy_pxxq_icon};

                    String[] module_img_text = {"就业状况","丧失劳力", "外出务工","劳动技能","求职需求", "培训需求"};
                    String[] moduleids = { "10060001", "10060002","10060003","10060004", "10060005","10060006"};

                    int[] module_imgColor = { R.color.ldjy_jyzk_color, R.color.ldjy_ssll_color,R.color.ldjy_wcwg_color,
                            R.color.ldjy_ldjn_color, R.color.ldjy_qzxq_color,R.color.ldjy_pxxq_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,
                            ModulepageActivity.class);
                    startActivity(intent);
                }
                else   if (position == 6) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.shbz_ylaobx_icon, R.string.shbz_ylbx_icon};

                    String[] module_img_text = {"养老保险","医疗保险"};
                    String[] moduleids = { "10070001", "10070002"};

                    int[] module_imgColor = { R.color.shbz_ylaobx_color, R.color.shbz_ylbx_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,
                            ModulepageActivity.class);
                    startActivity(intent);
                }
                else   if (position == 7) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.jhsy_syqk_icon, R.string.jhsy_hydx_icon,R.string.jhsy_cssd_icon,
                            R.string.jhsy_dszn_icon,R.string.jhsy_cszn_icon,R.string.jhsy_cjzn_icon};

                    String[] module_img_text = {"生育情况","怀孕对象","出生上报","独生之女","超生子女","残疾子女"};
                    String[] moduleids = { "10080001", "10080002","10080003", "10080004","10080005", "10080006"};

                    int[] module_imgColor = {R.color.jhsy_syqk_color, R.color.jhsy_hydx_color,R.color.jhsy_cssd_color,
                            R.color.jhsy_dszn_color,R.color.jhsy_cszn_color,R.color.jhsy_cjzn_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,
                            ModulepageActivity.class);
                    startActivity(intent);
                }
                else   if (position == 8) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.cyfw_mbhz_icon, R.string.cyfw_yjrq_icon,R.string.cyfw_yjrq_icon,
                            R.string.cyfw_mykh_icon,R.string.cyfw_jkxc_icon};

                    String[] module_img_text = {"慢病患者","吸烟人群","饮酒人群","免疫规划","健康宣传"};
                    String[] moduleids = { "10090001", "10090002","10090003", "10090004","10090005"};

                    int[] module_imgColor = {R.color.cyfw_mbhz_color, R.color.cyfw_yjrq_color,R.color.cyfw_yjrq_color,
                            R.color.cyfw_mykh_color,R.color.cyfw_jkxc_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,
                            ModulepageActivity.class);
                    startActivity(intent);
                }

                else   if (position == 9) {

                    Intent intent = new Intent();
                    int[] module_imgs = { R.string.zzgl_jfdj_icon, R.string.zzgl_jftj_icon,R.string.zzgl_tjsf_icon,
                            R.string.zzgl_sjbg_icon,R.string.zzgl_rwdd_icon};

                    String[] module_img_text = {"纠纷登记","纠纷调解","调解随访","事件报告","任务调度"};
                    String[] moduleids = { "10100001", "10100002","10100003", "10100004","10100005"};

                    int[] module_imgColor = {R.color.zzgl_jfdj_color, R.color.zzgl_jftj_color,R.color.zzgl_tjsf_color,
                            R.color.zzgl_sjbg_color,R.color.zzgl_rwdd_color};
                    intent.putExtra("imgs",module_imgs);
                    intent.putExtra("img_text",module_img_text);
                    intent.putExtra("imgColor",module_imgColor);
                    intent.putExtra("sysid",sysids[position]);
                    intent.putExtra("sysname",img_text[position]);
                    intent.putExtra("moduleids",moduleids);

                    intent.setClass(MainActivity.this,
                            ModulepageActivity.class);
                    startActivity(intent);
                }

            }

        });

    }

    private boolean getHomemodule() {

        String userID=MyApplication.tSysUsers.getUserID();
        String sessionID=MyApplication.tSysUsers.getSessionID();

        Gson gson = new Gson();
        HomemoduleVO homemoduleVO = new HomemoduleVO();
        homemoduleVO.setUserID(userID);
        homemoduleVO.setSessionID(sessionID);

        String route = gson.toJson(homemoduleVO);

        HomemoduleRetrofit homemoduleRetrofit=new HomemoduleRetrofit(MainActivity.this);

        Call<ResponseResults> call = homemoduleRetrofit.getServer(route);
        if (call==null)
        {
            return false;
        }
        call.enqueue(new Callback<ResponseResults>() {
            @Override
            public void onResponse(Call<ResponseResults> call, Response<ResponseResults> response) {

                ResponseResults responseResults=(ResponseResults)response.body();
                if (responseResults.getStatus().equals("failure"))
                {
                    UIHelper.ToastErrorMessage(mContext, "请求服务器出错！");
                    return;
                }

                initView();

            }

            @Override
            public void onFailure(Call<ResponseResults> call, Throwable t) {
                Log.i("sssss",t.getMessage());
            }
        });

        return true;

    }


    @Override
    public void onClick(View v) {

        UIHelper.ToastMessage(mContext,"我进来了"+v);


    }
}
