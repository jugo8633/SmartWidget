package dev.jugo.smartwidget.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public abstract class Device
{
	public static float getScaleSize(Context context)
	{
		float nDeviceWidth = getDeviceWidth();
		float nDisplayWidth = getDisplayWidth(context);
		float nScale = nDisplayWidth / nDeviceWidth;
		if (0 >= nScale)
		{
			nScale = 1;
		}
		return nScale;
	}

	public static int getDeviceWidth()
	{
		DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
		int dpWidth = (int) (displayMetrics.widthPixels / displayMetrics.density + 0.5);
		return dpWidth;
	}

	public static int getDeviceHeight()
	{
		DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
		int dpHeight = (int) (displayMetrics.heightPixels / displayMetrics.density + 0.5);
		return dpHeight;
	}

	public static int getDisplayWidth(Context context)
	{
		int nWidth;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		nWidth = metrics.widthPixels;
		metrics = null;
		return nWidth;
	}

	public int getDisplayHeight(Context context)
	{
		int nHeight;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		nHeight = metrics.heightPixels;
		metrics = null;
		return nHeight;
	}

	public static final int ScaleSize(Context context, int nSize)
	{
		float fScale = getScaleSize(context);
		int nResultSize = (int) Math.floor(nSize * fScale);
		return nResultSize;
	}

}
