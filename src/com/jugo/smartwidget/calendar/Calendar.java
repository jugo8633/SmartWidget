package com.jugo.smartwidget.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Calendar extends RelativeLayout
{
	private LinearLayout	llMain	= null;

	public Calendar(Context context)
	{
		super(context);
		init(context);
	}

	public Calendar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public Calendar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context)
	{
		llMain = new LinearLayout(context);
		llMain.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		this.addView(llMain);
	}
}
