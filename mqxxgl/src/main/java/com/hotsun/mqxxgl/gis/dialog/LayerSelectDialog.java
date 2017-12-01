package com.hotsun.mqxxgl.gis.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.adapter.DialogListAdapter;
import com.hotsun.mqxxgl.gis.presenter.LayerPresenter;

import java.util.List;

/**
 * Created by li on 2017/11/22.
 * LayerSelectDialog
 */

public class LayerSelectDialog extends Dialog{

    private Context mContext;
    private LayerPresenter layerPresenter;

    public LayerSelectDialog(@NonNull Context context, @StyleRes int themeResId, LayerPresenter presenter) {
        super(context, themeResId);
        this.mContext = context;
        this.layerPresenter = presenter;
    }

    protected LayerSelectDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gis_dialog_list);

        ListView listView =(ListView) findViewById(R.id.listview);

        DialogListAdapter adapter = new DialogListAdapter(mContext,layerPresenter);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
