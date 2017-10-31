package com.hotsun.mqxxgl.busi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.hotsun.mqxxgl.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnshow=(Button)findViewById(R.id.btnShow);
        btnshow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnShow:
                // 生成一个Intent对象
                Intent intent=new Intent();
                intent.putExtra("testIntent", "123");
                intent.setClass(MainActivity.this, LDActivity.class); //设置跳转的Activity
                startActivity(intent);
                break;

        }

    }
}
