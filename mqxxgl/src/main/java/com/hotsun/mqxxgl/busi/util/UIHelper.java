package com.hotsun.mqxxgl.busi.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hotsun.mqxxgl.R;
import com.hotsun.mqxxgl.gis.service.LiveNetworkMonitor;
import com.hotsun.mqxxgl.gis.service.NetworkMonitor;


public class UIHelper {

	/** dip转px */
	public static int dipToPx(Context context, int dip) {
		return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
	}

	/** px转dip */
	public static int pxToDip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/** 获取屏幕分辨率：宽 */
	public static int getScreenPixWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/** 获取屏幕分辨率：高 */
	public static int getScreenPixHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/** 隐藏软键盘 */
	public static void hideInputMethod(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/** 显示软键盘 */
	public static void showInputMethod(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		}
	}

	/** 多少时间后显示软键盘 */
	public static void showInputMethod(final View view, long delayMillis) {
		// 显示输入法
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				UIHelper.showInputMethod(view);

			}
		}, delayMillis);
	}

	/*
	 * 判断手机是否在锁屏状态 true锁屏 false未锁屏
	 */
	public static boolean isScreenLocked(Context c) {
		KeyguardManager mKeyguardManager = (KeyguardManager) c
				.getSystemService(Context.KEYGUARD_SERVICE);
		boolean bResult = !mKeyguardManager.inKeyguardRestrictedInputMode();

		return bResult;
	}

	// 判断程序在后台运行
	public static boolean isTopActivity(Context c) {
		String packageName = "com.hotsun.shglfw";
		ActivityManager activityManager = (ActivityManager) c
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		// Log.i("aa", tasksInfo.get(0).topActivity.getPackageName());
		if (tasksInfo.size() > 0) {
			// 应用程序位于堆栈的顶层
			if (packageName.equals(tasksInfo.get(0).topActivity
					.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	// 检测网络是否连接
	public static boolean isNetConntion(Context mContext) {
		NetworkMonitor mat = new LiveNetworkMonitor(mContext);
		if(!mat.isConnected())
		{
			UIHelper.ToastErrorMessage(mContext, "无网络连接，请检查网络！");
			return false;
		}
		return true;
	}

	/**
	 * 显示提示信息
	 * 
	 * @param context
	 * @param msg
	 */
	public static void ToastMessage(Context context, String msg) {
		ToastMessage(context, msg, 0);
	}

	// 错误消息
	public static void ToastErrorMessage(Context context, String msg) {
		ToastMessage(context, msg, R.drawable.g_icon_error);
	}

	// 好消息
	public static void ToastGoodMessage(Context context, String msg) {
		ToastMessage(context, msg, R.drawable.g_icon_succes);
	}

	// 显示自定义消息
	private static void ToastMessage(Context context, String msg, int iconResid) {
		View view = (View) LayoutInflater.from(context).inflate(
				R.layout.toast_view, null);
		TextView toast_msg = (TextView) view.findViewById(R.id.toast_msg);
		ImageView toast_icon = (ImageView) view.findViewById(R.id.toast_icon);
		if (iconResid > 0) {
			toast_icon.setVisibility(View.VISIBLE);
			toast_icon.setImageResource(iconResid);
		}
		toast_msg.setText(msg);

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
//		toast.setDuration(5000);
		toast.setView(view);
		toast.show();
	}

	// 退出程序
//	public static void Exit(Context context) {
//		AppManager.getAppManager().AppExit(context);
//	}

//	public static void startMainActivity(Context context) {
//		Intent in = new Intent();
//		in.setClass(context, MainActivity.class);
//		context.startActivity(in);
//	}
	
//	// 楼栋个案列表
//	public static void startLdgaList(Context context) {
//		Intent in = new Intent();
//		in.setClass(context, LdgaListActivity.class);
//		context.startActivity(in);
//	}
	
//	public static void openLdgaDetailFrame(Context context, LdgaBrief obj) {
//		Intent in = new Intent();
//		in.putExtra("ldgabrief", obj);
//		in.setClass(context, LdgaDetailFrame.class);
//		context.startActivity(in);
//	}
	
//	public static void openLdgaDetailFrameFromSo(Context context, LdgaSO obj) {
//		Intent in = new Intent();
//		in.putExtra("ldgasobrief", obj);
//		in.setClass(context, LdgaDetailFrame.class);
//		context.startActivity(in);
//	}

	// 房屋个案列表
//	public static void startFwgaList(Context context) {
//		Intent in = new Intent();
//		in.setClass(context, FwgaListActivity.class);
//		context.startActivity(in);
//	}
	
//	public static void openFwgaDetailFrame(Context context, FwgaBrief obj) {
//		Intent in = new Intent();
//		in.putExtra("fwgabrief", obj);
//		in.setClass(context, FwgaDetailFrame.class);
//		context.startActivity(in);
//	}
	
//	public static void openFwgaDetailFrameFromSo(Context context, FwgaSO obj) {
//		Intent in = new Intent();
//		in.putExtra("fwgasobrief", obj);
//		in.setClass(context, FwgaDetailFrame.class);
//		context.startActivity(in);
//	}

	// 人口个案列表
//	public static void startRkgaList(Context context) {
//		Intent in = new Intent();
//		in.setClass(context, RkgaListActivity.class);
//		context.startActivity(in);
//	}
	
//	public static void openRkgaDetailFrame(Context context, RkgaBrief obj) {
//		Intent in = new Intent();
//		in.putExtra("rkgabrief", obj);
//		in.setClass(context, RkgaDetailFrame.class);
//		context.startActivity(in);
//	}
	
//	public static void openRkgaDetailFrameFromSo(Context context, RkgaSO obj) {
//		Intent in = new Intent();
//		in.putExtra("rkgasobrief", obj);
//		in.setClass(context, RkgaDetailFrame.class);
//		context.startActivity(in);
//	}

}
