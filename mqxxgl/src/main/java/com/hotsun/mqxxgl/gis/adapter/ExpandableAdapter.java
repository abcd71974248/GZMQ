package com.hotsun.mqxxgl.gis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.presenter.LayerPresenter;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by li on 2017/11/3.
 * ExpandableAdapter
 */

public class ExpandableAdapter extends BaseExpandableListAdapter implements LayerPresenter.ICheckBox{

    private Context mContext;
    private List<File> groups;
    private HashMap<String, List<File>> childs;
    private HashMap<String, Boolean> checkstates = new HashMap<>();
    private LayerPresenter layerPresenter;

    public ExpandableAdapter(Context context,List<File> folds,HashMap<String, List<File>> files,HashMap<String, Boolean> states,LayerPresenter presenter){
        this.mContext = context;
        this.groups = folds;
        this.childs = files;
        this.checkstates = states;
        this.layerPresenter = presenter;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childs.get(groups.get(groupPosition).getPath()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String key = groups.get(groupPosition).getPath();
        return childs.get(key).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if(null == convertView){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gis_item_group,null);
            groupHolder = new GroupHolder();

            groupHolder.gText =(TextView) convertView.findViewById(R.id.id_group_txt);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.gText.setText(groups.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if(null == convertView){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gis_item_child,null);
            childHolder = new ChildHolder();

            childHolder.checkBox =(CheckBox) convertView.findViewById(R.id.cb_child);
            childHolder.cText =(TextView) convertView.findViewById(R.id.id_child_txt);
            childHolder.imageView =(ImageView) convertView.findViewById(R.id.child_dw_extent);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildHolder) convertView.getTag();
        }

        final String key = groups.get(groupPosition).getPath();
        List<File> list = childs.get(key);
        final File file = list.get(childPosition);
        String name = file.getName().split("\\.")[0];
        childHolder.cText.setText(name);
        if(checkstates.get(key) == null){
            childHolder.checkBox.setChecked(false);
        }else{
            childHolder.checkBox.setChecked(checkstates.get(key));
        }
        childHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkstates.put(key,isChecked);
                if(isChecked){
                    layerPresenter.loadGeodatabase(file);
                }else{
                    layerPresenter.removeGeodatabase(file);
                }
            }
        });

        childHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layerPresenter.zoomToLayer(file);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public HashMap<String, Boolean> getCheckStates() {
        return checkstates;
    }

    private class GroupHolder {
        TextView gText;
    }
    private class ChildHolder{
        TextView cText;
        CheckBox checkBox;
        ImageView imageView;
    }

}
