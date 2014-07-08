package com.jugo.smartwidget.common;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.View;

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
}
