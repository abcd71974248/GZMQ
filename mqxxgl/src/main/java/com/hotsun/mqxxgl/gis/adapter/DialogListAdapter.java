package com.hotsun.mqxxgl.gis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.presenter.LayerPresenter;

/**
 * Created by li on 2017/11/22.
 * DialogListAdapter
 */

public class DialogListAdapter extends BaseAdapter{

    private LayerPresenter layerPresenter;
    private Context mContext;
    public DialogListAdapter(Context context,LayerPresenter presenter){
        this.layerPresenter = presenter;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return layerPresenter.myFeatureLayers.size();
    }

    @Override
    public Object getItem(int position) {
        return layerPresenter.myFeatureLayers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(null == convertView){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gis_listview_item, null);
            viewHolder.textView =(TextView) convertView.findViewById(R.id.tv_list);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(layerPresenter.myFeatureLayers.get(position).getLayerName());
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}
