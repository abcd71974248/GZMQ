package com.hotsun.mqxxgl.busi.presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.activity.LDActivity;
import com.hotsun.mqxxgl.busi.activity.LdViewActivity;
import com.hotsun.mqxxgl.busi.model.ResponseResults;
import com.hotsun.mqxxgl.busi.model.requestParams.GetSingleDataVO;
import com.hotsun.mqxxgl.busi.model.sysbeans.AddResponseResults;
import com.hotsun.mqxxgl.busi.service.ldxxgl.DeleteLdxxRetrofit;
import com.hotsun.mqxxgl.busi.service.ldxxgl.GetLdxxViewRetrofit;
import com.hotsun.mqxxgl.busi.util.UIHelper;
import com.hotsun.mqxxgl.busi.view.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.id.list;


/**
 * @Description:gridview的Adapter
 * @author
 */
public  class MyLdListAdapter extends BaseAdapter implements View.OnClickListener {

	private Context mContext;
	public int clickPosition = -1;


	private List<Map<String, String>> results;

	public MyLdListAdapter(Context mContext,List<Map<String, String>> results) {
		super();
        this.results=results;
		this.mContext = mContext;
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
			convertView = View.inflate(mContext, R.layout.include_ldga_item, null);
			vh = new MyViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (MyViewHolder) convertView.getTag();
		}
		vh.tv_cunmc.setText(results.get(position).get("ldid"));
		vh.tv_ldmc.setText(results.get(position).get("ldmc"));
		vh.tv_zumc.setText(results.get(position).get("zumc"));
		//刷新adapter的时候，getview重新执行，此时对在点击中标记的position做处理
		if (clickPosition == position) {//当条目为刚才点击的条目时
			if (vh.selectorImg.isSelected()) {//当条目状态图标为选中时，说明该条目处于展开状态，此时让它隐藏，并切换状态图标的状态。
				vh.selectorImg.setSelected(false);
				vh.selectorImg.setText(R.string.icon_caret_right);
				vh.ll_hide.setVisibility(View.GONE);
				clickPosition=-1;//隐藏布局后需要把标记的position去除掉，否则，滑动listview让该条目划出屏幕范围，
				// 当该条目重新进入屏幕后，会重新恢复原来的显示状态。经过打log可知每次else都执行一次 （条目第二次进入屏幕时会在getview中寻找他自己的状态，相当于重新执行一次getview）
				//因为每次滑动的时候没标记得position填充会执行click
			} else {//当状态条目处于未选中时，说明条目处于未展开状态，此时让他展开。同时切换状态图标的状态。
				vh.selectorImg.setSelected(true);
				vh.selectorImg.setText(R.string.icon_caret_desc);
				vh.ll_hide.setVisibility(View.VISIBLE);

			}
//                ObjectAnimator//
//                        .ofInt(vh.ll_hide, "rotationX", 0.0F, 360.0F)//
//                        .setDuration(500)//
//                        .start();
			// vh.selectorImg.setSelected(true);
		} else {//当填充的条目position不是刚才点击所标记的position时，让其隐藏，状态图标为false。

			//每次滑动的时候没标记得position填充会执行此处，把状态改变。所以如果在以上的if (vh.selectorImg.isSelected()) {}中不设置clickPosition=-1；则条目再次进入屏幕后，还是会进入clickposition==position的逻辑中
			//而之前的滑动（未标记条目的填充）时，执行此处逻辑，已经把状态图片的selected置为false。所以上面的else中的逻辑会在标记过的条目第二次进入屏幕时执行，如果之前的状态是显示，是没什么影响的，再显示一次而已，用户看不出来，但是如果是隐藏状态，就会被重新显示出来
			vh.ll_hide.setVisibility(View.GONE);
			vh.selectorImg.setSelected(false);
			vh.selectorImg.setText(R.string.icon_caret_right);

		}
		vh.hide_3.setOnClickListener(this);
		vh.selectorImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				clickPosition = position;//记录点击的position
				notifyDataSetChanged();//刷新adapter重新填充条目。在重新填充的过程中，被记录的position会做展开或隐藏的动作，具体的判断看上面代码
				//在此处需要明确的一点是，当adapter执行刷新操作时，整个getview方法会重新执行，也就是条目重新做一次初始化被填充数据。
				//所以标记position，不会对条目产生影响，执行刷新后 ，条目重新填充当，填充至所标记的position时，我们对他处理，达到展开和隐藏的目的。
				//明确这一点后，每次点击代码执行逻辑就是 onclick（）---》getview（）




			}
		});
		return convertView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.hide_3:

				deleteLdxx(results.get(clickPosition).get("ldid"));

				results.remove(clickPosition);
				notifyDataSetChanged();
				break;
		}
	}


	class MyViewHolder {
		View itemView;
		TextView tv_cunmc;
		TextView tv_ldmc;
		TextView tv_zumc;
		TextView  hide_3;
		TextView selectorImg;
		LinearLayout ll_hide;

		public MyViewHolder(View itemView) {

			this.itemView = itemView;
			tv_cunmc =(TextView) itemView.findViewById(R.id.activity_cunmc);
			tv_zumc =(TextView) itemView.findViewById(R.id.ldxx_zumc);
			tv_ldmc=(TextView) itemView.findViewById(R.id.ldxx_ldname);
			selectorImg = BaseViewHolder.get(itemView, R.id.btn_ldxx_desc);
			hide_3 = (TextView) itemView.findViewById(R.id.hide_3);
			ll_hide = (LinearLayout) itemView.findViewById(R.id.ll_hide);
		}
	}




	private void deleteLdxx(String ldid) {


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
		getLdxxVO.setDataid(ldid);

		String route = gson.toJson(getLdxxVO);



		DeleteLdxxRetrofit deleteLdxxRetrofit=new DeleteLdxxRetrofit(mContext);

		Call<AddResponseResults> call = deleteLdxxRetrofit.getServer(route);
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
					UIHelper.ToastErrorMessage(mContext, responseResults.getMsg());
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
