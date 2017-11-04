package com.hotsun.mqxxgl.busi.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.busi.view.BaseViewHolder;



/**
 * @Description:gridview的Adapter
 * @author
 */
public class MyGridAdapter extends BaseAdapter {
	private Context mContext;

	private String[] img_text ;

	private int[] imgs;

	private int[] imgColor ;

//	R.string[] asas={R.string.app_name}

	public MyGridAdapter(Context mContext,int[] imgs,String[] img_text,int[] imgColor) {
		super();
		this.mContext = mContext;
		this.imgs = imgs;
		this.imgColor = imgColor;
		this.img_text = img_text;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid_item, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		TextView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		iv.setText(imgs[position]);
		iv.setTextColor(mContext.getResources().getColor(imgColor[position]));
		tv.setText(img_text[position]);
		return convertView;
	}

}
