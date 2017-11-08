package com.hotsun.mqxxgl.busi.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hotsun.mqxxgl.R;

import org.w3c.dom.Text;


public class LdViewActivity extends AppCompatActivity{

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldview);

        TextView txtTitle=(TextView)findViewById(R.id.text_title) ;
        txtTitle.setText("楼栋信息管理");
        String ldid=getIntent().getStringExtra("ldid");

        TextView ldview=(TextView)findViewById(R.id.addld_cunmctext);

        ldview.setText(ldid);

        initView();

    }
    private void initView() {


    }



}
