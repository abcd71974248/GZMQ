package com.hotsun.mqxxgl.gis.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esri.android.map.FeatureLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.geodatabase.GeodatabaseFeature;
import com.esri.core.geodatabase.GeodatabaseFeatureTable;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.FeatureTemplate;
import com.esri.core.map.FeatureType;
import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.Symbol;
import com.esri.core.table.TableException;

import java.util.List;

/**
 * 图层标绘工具类
 */
public class SymbolUtil {

	public enum MODE {
		RIGHT, LEFT, TOP, BOTTOM
	}

	/**
	 * 返回组件的截图
	 *  "vw 传入要显示的布局View
	 * @return PictureMarkerSymbol类
	 */
	public static PictureMarkerSymbol pictureSymobel(Context context,
													 int layoutID) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View vw = inflater.inflate(layoutID, null);
		Bitmap bt = convertViewToBitmap(vw);
		BitmapDrawable temp = new BitmapDrawable(context.getResources(), bt);

		return new PictureMarkerSymbol(temp);
	}

	/**
	 * @param view 要标绘的view对象
	 * @return PictureMarkerSymbol类
	 */
	public static PictureMarkerSymbol pictureSymobel(Context context, View view) {

		Bitmap bt = convertViewToBitmap(view);
		BitmapDrawable temp = new BitmapDrawable(context.getResources(), bt);

		return new PictureMarkerSymbol(temp);
	}

	/**
	 * @param label 标绘文字内容
	 * @param color 字体颜色
	 * @param size 字体大小
	 * @param imgInt 图片资源
	 * @param mode 显示位置
	 */
	public static PictureMarkerSymbol TextPicSymobel(Context context,
													 CharSequence label, int color, float size, int imgInt, MODE mode) {

		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		ImageView imgView = new ImageView(context);
		imgView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		Drawable db = context.getResources().getDrawable(imgInt);
		imgView.setImageDrawable(db);

		TextView txtView = new TextView(context);
		txtView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		txtView.setText(label);
		txtView.setTextColor(color);
		txtView.setTextSize(size);

		switch (mode) {

			case RIGHT:
				layout.addView(txtView);
				layout.addView(imgView);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				break;
			case LEFT:
				layout.addView(imgView);
				layout.addView(txtView);
				layout.setGravity(Gravity.CENTER);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				break;
			case TOP:
				layout.addView(txtView);
				layout.addView(imgView);
				layout.setOrientation(LinearLayout.VERTICAL);
				break;
			case BOTTOM:
				layout.addView(imgView);
				layout.addView(txtView);
				layout.setOrientation(LinearLayout.VERTICAL);
				break;
		}
		Bitmap bt = convertViewToBitmap(layout);
		BitmapDrawable temp = new BitmapDrawable(context.getResources(), bt);

		return new PictureMarkerSymbol(temp);
	}

	/**
	 * @param label 标绘文字内容
	 * @param color 字体颜色
	 * @param size 字体大小
	 * @param mode 显示位置
	 */
	public static PictureMarkerSymbol TextPicSymobel(Context context,
													 CharSequence label, int color, float size, MODE mode) {

		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		TextView txtView = new TextView(context);
		txtView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		txtView.setText(label);
		txtView.setTextSize(18);
		txtView.setTextColor(color);
		txtView.setTextSize(size);
		if (label.equals("起点")) {
			layout.setPadding(80, 0, 0, 0);
		} else {
			layout.setPadding(120, 0, 0, 0);
		}
		switch (mode) {

			case RIGHT:
				layout.addView(txtView);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				break;
			case LEFT:
				layout.addView(txtView);
				layout.setGravity(Gravity.CENTER);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				break;
			case TOP:
				layout.addView(txtView);
				layout.setOrientation(LinearLayout.VERTICAL);
				break;
			case BOTTOM:
				layout.addView(txtView);
				layout.setOrientation(LinearLayout.VERTICAL);
				break;
		}
		Bitmap bt = convertViewToBitmap(layout);
		BitmapDrawable temp = new BitmapDrawable(context.getResources(), bt);

		return new PictureMarkerSymbol(temp);
	}

	/**
	 * @param label 标绘文字内容
	 * @param color 字体颜色
	 * @param size 字体大小
	 * @param mode  显示位置
	 */
	public static PictureMarkerSymbol TextPicSymobelArea(Context context,
														 CharSequence label, int color, float size, MODE mode) {

		LinearLayout layout = new LinearLayout(context);
		layout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		TextView txtView = new TextView(context);
		txtView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		txtView.setText(label);
		txtView.setTextColor(color);
		txtView.setTextSize(size);
		layout.setPadding(200, 0, 0, 0);
		switch (mode) {

			case RIGHT:
				layout.addView(txtView);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				break;
			case LEFT:
				layout.addView(txtView);
				layout.setGravity(Gravity.CENTER);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				break;
			case TOP:
				layout.addView(txtView);
				layout.setOrientation(LinearLayout.VERTICAL);
				break;
			case BOTTOM:
				layout.addView(txtView);
				layout.setOrientation(LinearLayout.VERTICAL);
				break;
		}
		Bitmap bt = convertViewToBitmap(layout);
		BitmapDrawable temp = new BitmapDrawable(context.getResources(), bt);

		return new PictureMarkerSymbol(temp);
	}

	public PictureMarkerSymbol TextPicSymobel(Context context,
											  CharSequence label, int color, int size) {
		TextView txtView = new TextView(context);
		txtView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		txtView.setText(label);
		txtView.setTextColor(color);
		txtView.setTextSize(size);
		Bitmap bt = convertViewToBitmap(txtView);
		BitmapDrawable temp = new BitmapDrawable(context.getResources(), bt);
		return new PictureMarkerSymbol(temp);
	}

	/**
	 * @param label 标绘的文字
	 * @param imgInt 标绘的图片资源
	 * @param mode 文字的摆放位置
	 */
	public static PictureMarkerSymbol TextPicSymobel(Context context,
													 CharSequence label, int imgInt, MODE mode) {
		return TextPicSymobel(context, label, Color.BLACK, 15, mode);
	}

	public static Bitmap createBitmapfromSymbol(Symbol symbol,
												ArcGISFeatureLayer layer) {
		Bitmap bitmap = null;
		// if (layer.getGeometryType().equals(Geometry.Type.POINT)) {
		// Point pt = new Point(20,20);
		// bitmap = SymbolHelper.getLegendImage(symbol,pt, 50, 50,Color.WHITE);
		// }
		// else if (layer.getGeometryType().equals(Geometry.Type.POLYLINE)) {
		// Polyline polyline = new Polyline();
		// polyline.startPath(0,0);
		// polyline.lineTo(40,40);
		// bitmap = SymbolHelper.getLegendImage(symbol,polyline, 50, 50,
		// Color.WHITE);
		// }
		// else if (layer.getGeometryType().equals(Geometry.Type.POLYGON)){
		// Polygon polygon = new Polygon();
		// polygon.startPath(0, 0);
		// polygon.lineTo(40, 0);
		// polygon.lineTo(40, 40);
		// polygon.lineTo(0, 40);
		// polygon.lineTo(0, 0);
		// bitmap = SymbolHelper.getLegendImage(symbol,polygon, 50, 50,
		// Color.WHITE);
		// }
		return bitmap;
	}

	private static Bitmap convertViewToBitmap(View view) {
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
				View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.invalidate();
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

}
