package com.hotsun.mqxxgl.busi.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.activity.AddLdActivity;
import com.hotsun.mqxxgl.busi.activity.DistrictPickerActivity;
import com.hotsun.mqxxgl.busi.activity.LDActivity;
import com.hotsun.mqxxgl.busi.activity.OftenUseActivity;
import com.hotsun.mqxxgl.busi.model.requestParams.GetSingleDataVO;
import com.hotsun.mqxxgl.busi.model.sysbeans.AddResponseResults;
import com.hotsun.mqxxgl.busi.service.DeleteMdlxxRetrofit;
import com.hotsun.mqxxgl.busi.service.ldxxgl.DeleteLdxxRetrofit;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.busi.view.BaseViewHolder;
import com.hotsun.mqxxgl.busi.view.IconFontTextview;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * @Description:gridview的Adapter
 * @author
 */
public  class OftenMdlListAdapter extends BaseAdapter implements View.OnClickListener {

	private Context mContext;
	private boolean isShow;

	private Map<String,String> mdlidMap = new HashMap<>();

	private List<Map<String, String>> results;
	private int clickPosition = -1;

	private Map<Integer,Boolean> map=new HashMap<>();

	public OftenMdlListAdapter(Context mContext, List<Map<String, String>> results,boolean isShow) {
		super();
        this.results=results;
		this.mContext = mContext;
		this.isShow = isShow;
	}
	public void updateView( List<Map<String, String>> results ){
		this.results = results;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return results.size();
	}

	@Override
	public Object getItem(int position) {
		return results.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final MyViewHolder vh;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.include_often_item, null);
			vh = new MyViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (MyViewHolder) convertView.getTag();
		}
		String mdlid = results.get(position).get("MDLID");
		vh.tv_name.setText(results.get(position).get("MDLNAME"));
		if(mdlid.equals("10010001")){
			vh.tv_icon.setText(R.string.jtrkgl_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.cyfw_color));
		}else if(mdlid.equals("10010002")){
			vh.tv_icon.setText(R.string.lset_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.lset_color));
		}else if(mdlid.equals("10010003")){
			vh.tv_icon.setText(R.string.kclr_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.kclr_color));
		}else if(mdlid.equals("10010004")){
			vh.tv_icon.setText(R.string.cjrq_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.cjrq_color));
		}else if(mdlid.equals("10020001")){
			vh.tv_icon.setText(R.string.ldxxgl_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.ldxxgl_color));
		}else if(mdlid.equals("10020002")){
			vh.tv_icon.setText(R.string.ldssgl_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.ldssgl_color));
		}else if(mdlid.equals("10030001")){
			vh.tv_icon.setText(R.string.fwxxgl_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.fwxxgl_color));
		}else if(mdlid.equals("10030002")){
			vh.tv_icon.setText(R.string.shhjgl_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.shhjgl_color));
		}else if(mdlid.equals("10040001")){
			vh.tv_icon.setText(R.string.qygk_jbxx_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.qygk_jbxx_color));
		}else if(mdlid.equals("10040002")){
			vh.tv_icon.setText(R.string.qygk_fwry_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.qygk_fwry_color));
		}else if(mdlid.equals("10040003")){
			vh.tv_icon.setText(R.string.qygk_xxxx_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.qygk_xxxx_color));
		}else if(mdlid.equals("10040004")){
			vh.tv_icon.setText(R.string.qygk_rkqk_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.qygk_rkqk_color));
		}else if(mdlid.equals("10040005")){
			vh.tv_icon.setText(R.string.qygk_sdxx_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.qygk_sdxx_color));
		}else if(mdlid.equals("10040006")){
			vh.tv_icon.setText(R.string.qygk_cgxx_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.qygk_cgxx_color));
		}else if(mdlid.equals("10040007")){
			vh.tv_icon.setText(R.string.qygk_jtjj_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.qygk_jtjj_color));
		}else if(mdlid.equals("10040008")){
			vh.tv_icon.setText(R.string.qygk_tsfw_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.qygk_tsfw_color));
		}else if(mdlid.equals("10050001")){
			vh.tv_icon.setText(R.string.pjjy_snrq_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.pjjy_snrq_color));
		}else if(mdlid.equals("10050002")){
			vh.tv_icon.setText(R.string.pjjy_jycd_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.pjjy_jycd_color));
		}else if(mdlid.equals("10050003")){
			vh.tv_icon.setText(R.string.pjjy_pkxs_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.pjjy_pkxs_color));
		}else if(mdlid.equals("10060001")){
			vh.tv_icon.setText(R.string.ldjy_jyzk_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.ldjy_jyzk_color));
		}else if(mdlid.equals("10060002")){
			vh.tv_icon.setText(R.string.ldjy_ssll_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.ldjy_ssll_color));
		}else if(mdlid.equals("10060003")){
			vh.tv_icon.setText(R.string.ldjy_wcwg_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.ldjy_wcwg_color));
		}else if(mdlid.equals("10060004")){
			vh.tv_icon.setText(R.string.ldjy_ldjn_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.ldjy_ldjn_color));
		}else if(mdlid.equals("10060005")){
			vh.tv_icon.setText(R.string.ldjy_qzxq_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.ldjy_qzxq_color));
		}else if(mdlid.equals("10060006")){
			vh.tv_icon.setText(R.string.ldjy_pxxq_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.ldjy_pxxq_color));
		}else if(mdlid.equals("10070001")){
			vh.tv_icon.setText(R.string.shbz_ylaobx_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.shbz_ylaobx_color));
		}else if(mdlid.equals("10070002")){
			vh.tv_icon.setText(R.string.shbz_ylbx_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.shbz_ylbx_color));
		}else if(mdlid.equals("10080001")){
			vh.tv_icon.setText(R.string.jhsy_syqk_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.jhsy_syqk_color));
		}else if(mdlid.equals("10080002")){
			vh.tv_icon.setText(R.string.jhsy_hydx_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.jhsy_hydx_color));
		}else if(mdlid.equals("10080003")){
			vh.tv_icon.setText(R.string.jhsy_cssd_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.jhsy_cssd_color));
		}else if(mdlid.equals("10080004")){
			vh.tv_icon.setText(R.string.jhsy_dszn_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.jhsy_dszn_color));
		}else if(mdlid.equals("10080005")){
			vh.tv_icon.setText(R.string.jhsy_cszn_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.jhsy_cszn_color));
		}else if(mdlid.equals("10080006")){
			vh.tv_icon.setText(R.string.jhsy_cjzn_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.jhsy_cjzn_color));
		}else if(mdlid.equals("10090001")){
			vh.tv_icon.setText(R.string.cyfw_mbhz_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.cyfw_mbhz_color));
		}else if(mdlid.equals("10090002")){
			vh.tv_icon.setText(R.string.cyfw_xyrq_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.cyfw_xyrq_color));
		}else if(mdlid.equals("10090003")){
			vh.tv_icon.setText(R.string.cyfw_yjrq_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.cyfw_yjrq_color));
		}else if(mdlid.equals("10090004")){
			vh.tv_icon.setText(R.string.cyfw_mykh_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.cyfw_mykh_color));
		}else if(mdlid.equals("10090005")){
			vh.tv_icon.setText(R.string.cyfw_jkxc_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.cyfw_jkxc_color));
		}else if(mdlid.equals("10100001")){
			vh.tv_icon.setText(R.string.zzgl_jfdj_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.zzgl_jfdj_color));
		}else if(mdlid.equals("10100002")){
			vh.tv_icon.setText(R.string.zzgl_jftj_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.zzgl_jftj_color));
		}else if(mdlid.equals("10100003")){
			vh.tv_icon.setText(R.string.zzgl_tjsf_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.zzgl_tjsf_color));
		}else if(mdlid.equals("10100004")){
			vh.tv_icon.setText(R.string.zzgl_sjbg_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.zzgl_sjbg_color));
		}else if(mdlid.equals("10100005")){
			vh.tv_icon.setText(R.string.zzgl_rwdd_icon);
			vh.tv_icon.setTextColor(mContext.getResources().getColor(R.color.zzgl_rwdd_color));
		}

		vh.tv_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked==true){
					map.put(position,true);
					mdlidMap.put(results.get(position).get("MDLID"),results.get(position).get("MDLID"));
				}else {
					map.remove(position);
					mdlidMap.remove(results.get(position).get("MDLID"));
				}
			}
		});

		vh.tv_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				deleteMdlxx(results.get(position).get("MDLID"));

			}
		});

		if(map!=null&&map.containsKey(position)){
			vh.tv_check.setChecked(true);
		}else {
			vh.tv_check.setChecked(false);
		}

		if(isShow){
			vh.tv_check.setVisibility(View.VISIBLE);
			vh.tv_delete.setVisibility(View.GONE);
		}else{
			vh.tv_check.setVisibility(View.GONE);
			vh.tv_delete.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	public String getMdlids(){
		String mdlids = "";
		if(mdlidMap.size() < 1){
			return "";
		}
		for (Map.Entry<String, String> entry : mdlidMap.entrySet()) {
			mdlids = mdlids + entry.getValue() + ",";
		}
		mdlids = mdlids.substring(0,mdlids.length()-1);
		mdlidMap.clear();
		return mdlids;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.hide_3:

				break;
		}
	}


	class MyViewHolder {
		View itemView;
		IconFontTextview tv_icon;
		TextView tv_name;
		CheckBox tv_check;
		IconFontTextview tv_delete;


		public MyViewHolder(View itemView) {

			this.itemView = itemView;
			tv_icon =(IconFontTextview) itemView.findViewById(R.id.oftenlist_icon);
			tv_name =(TextView) itemView.findViewById(R.id.oftenlist_name);
			tv_check=(CheckBox) itemView.findViewById(R.id.oftenlist_check);
			tv_delete=(IconFontTextview) itemView.findViewById(R.id.oftenlist_delete);
		}
	}




	private void deleteMdlxx(String mdlid) {

		if (MyApplication.tSysUsers==null)
		{
			UIHelper.ToastErrorMessage(mContext,"操作失败，请重新登录!");
			return ;
		}
		String userID= MyApplication.tSysUsers.getUserID();
		String sessionID=MyApplication.tSysUsers.getSessionID();

		Gson gson = new Gson();
		GetSingleDataVO getLdxxVO = new GetSingleDataVO();


		getLdxxVO.setUserID(userID);
		getLdxxVO.setSessionID(sessionID);
		getLdxxVO.setDataid(mdlid);

		String route = gson.toJson(getLdxxVO);


		DeleteMdlxxRetrofit deleteMdlxxRetrofit = new DeleteMdlxxRetrofit(mContext);

		Call<AddResponseResults> call = deleteMdlxxRetrofit.getServer(route);
		call.enqueue(new Callback<AddResponseResults>() {
			@Override
			public void onResponse(Call<AddResponseResults> call, Response<AddResponseResults> response) {

				AddResponseResults responseResults=(AddResponseResults)response.body();
				if (responseResults.getStatus().equals("false"))
				{
					UIHelper.ToastErrorMessage(mContext, responseResults.getMsg());
					return;
				}

				if (responseResults.getStatus().equals("success"))
				{
					((OftenUseActivity)mContext).initView();
					return;
				}


			}

			@Override
			public void onFailure(Call<AddResponseResults> call, Throwable t) {
				UIHelper.ToastErrorMessage(mContext,t.getMessage());
				return;
			}
		});


	}



}
