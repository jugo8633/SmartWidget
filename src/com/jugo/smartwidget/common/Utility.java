package com.jugo.smartwidget.common;

public abstract class Utility
{
	private static int	mnUserId	= 2048;

	public static int getUserId()
	{
		return (++mnUserId);
	}
}
