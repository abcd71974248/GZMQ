package com.hotsun.mqxxgl.gis.util;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;

import com.hotsun.mqxxgl.MyApplication;
import com.hotsun.mqxxgl.common.ScreenTool;

/**
 * Created by li on 2017/11/22.
 * DialogUtil
 */

public class DialogUtil {

    /** dialog 宽度和高度设置
     * pwidth 平板设备宽度
     * mwidth 手机设备宽度
     * */
    public static void setDialogCenter(Context context, Dialog dialog, double pwidth, double mwidth) {
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        ScreenTool.Screen screen = MyApplication.screen;
        if (isPad(context)) {
            params.width = (int) (screen.getWidthPixels() * pwidth);
        } else {
            params.width = (int) (screen.getWidthPixels() * mwidth);
        }
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    /**
     * 判断设备是否为大于6.0屏幕的设备
     */
    @SuppressWarnings("unused")
    public static boolean isPad(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        @SuppressWarnings("deprecation")
        float screenWidth = display.getWidth();
        @SuppressWarnings("deprecation")
        float screenHeight = display.getHeight();

        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        if (screenInches >= 6.0) {
            return true;
        }
        return false;
    }

}
