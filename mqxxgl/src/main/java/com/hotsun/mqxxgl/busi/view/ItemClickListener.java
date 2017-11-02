package com.hotsun.mqxxgl.busi.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;

/**
 * Created by yuan on 2017-10-31.
 */

public final class ItemClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView listView = (ListView) parent;
            HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
    //                    String appname = data.get("name").toString();
    //                    Toast.makeText(getApplicationContext(), appname + data.get("count").toString(), Toast.LENGTH_SHORT).show();
        }
}

