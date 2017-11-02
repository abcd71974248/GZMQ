package com.hotsun.mqxxgl.busi.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by yuan on 2017-11-01.
 */

public class IconFontTextview extends AppCompatTextView {
    public IconFontTextview(Context context) {
        super(context);          init(context);
    }
    public IconFontTextview(Context context, AttributeSet attrs)
    {        super(context, attrs);         init(context);
    }
    public IconFontTextview(Context context, AttributeSet attrs, int defStyleAttr)
    {          super(context, attrs, defStyleAttr);          init(context);
    }
    private void init(Context context){
        Typeface iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/icomoon.ttf");
        setTypeface(iconfont);

    } }

