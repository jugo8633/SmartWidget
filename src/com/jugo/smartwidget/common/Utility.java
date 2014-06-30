package com.jugo.smartwidget.common;

import android.graphics.Rect;
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
}
