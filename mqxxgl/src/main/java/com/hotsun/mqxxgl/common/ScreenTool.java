package com.hotsun.mqxxgl.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * Created by li on 2017/5/31.
 * 设置尺寸类
 */
public class ScreenTool {

	public static Screen getScreenPix(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels);
	}

	public static class Screen {
		public int widthPixels;

		public int getWidthPixels() {
			return widthPixels;
		}

		public void setWidthPixels(int widthPixels) {
			this.widthPixels = widthPixels;
		}

		public int getHeightPixels() {
			return heightPixels;
		}

		public void setHeightPixels(int heightPixels) {
			this.heightPixels = heightPixels;
		}

		public int heightPixels;

		public Screen() {
		}

		public Screen(int widthPixels, int heightPixels) {
			this.widthPixels = widthPixels;
			this.heightPixels = heightPixels;
		}

		@Override
		public String toString() {
			return "(" + widthPixels + "," + heightPixels + ")";
		}
	}
}
