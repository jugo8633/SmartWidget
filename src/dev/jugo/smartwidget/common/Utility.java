package dev.jugo.smartwidget.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public abstract class Utility
{
	private static int	mnUserId	= 2048;

	public static int getUserId()
	{
		return (++mnUserId);
	}

	public static Rect getViewRect(View view)
	{
		int[] l = new int[2];
		view.getLocationOnScreen(l);
		Rect rect = new Rect(l[0], l[1], l[0] + view.getWidth(), l[1] + view.getHeight());
		l = null;
		return rect;
	}

	public static boolean isViewContains(int nX, int nY, View view)
	{
		Rect rect = getViewRect(view);
		return rect.contains(nX, nY);
	}

	synchronized public static void notify(Handler handler, int nWhat, int nEvent, int nPosition, Object object)
	{
		if (null != handler)
		{
			Message msg = new Message();
			msg.what = nWhat;
			msg.arg1 = nEvent;
			msg.arg2 = nPosition;
			msg.obj = object;
			handler.sendMessage(msg);
		}
		else
		{
			Logs.showTrace("Event Handler Error: Invalid Handler !!");
		}
	}
	
	public static float ScaleWidthSize(Context context, float nSize)
	{
		/** get device real size **/
		DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
		float nDeviceWidth = (float) (displayMetrics.widthPixels / displayMetrics.density + 0.5);
		
		/** get device resolution **/
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		float nDisplayWidth = metrics.widthPixels;
		metrics = null;
		
		float nScale = nDisplayWidth / nDeviceWidth;
		if (0 >= nScale)
		{
			nScale = 1.0f;
		}

		float nScaleSize = (float) Math.floor(nSize * nScale);
		return nScaleSize;
	}
	
	public static float ScaleHeightSize(Context context, float nSize)
	{
		/** get device real size **/
		DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
		float nDeviceHeight = (float) (displayMetrics.heightPixels / displayMetrics.density + 0.5);
		
		/** get device resolution **/
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		float nDisplayHeight = metrics.heightPixels;
		metrics = null;
		
		float nScale = nDisplayHeight / nDeviceHeight;
		if (0 >= nScale)
		{
			nScale = 1.0f;
		}

		float nScaleSize = (float) Math.floor(nSize * nScale);
		return nScaleSize;
	}
}
