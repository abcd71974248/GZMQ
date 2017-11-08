package com.hotsun.mqxxgl.gis.util;

import android.view.View;
import android.widget.FrameLayout;

import com.hotsun.mqxxgl.MyApplication;

/**
 * Created by li on 2017/11/3.
 * 设置工具类
 */

public class ViewUtil {

    public static void setViewPrams(View view){
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = (int) (MyApplication.screen.getWidthPixels() * 0.2);
        layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
    }
}
