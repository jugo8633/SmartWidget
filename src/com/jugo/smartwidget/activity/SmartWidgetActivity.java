package com.jugo.smartwidget.activity;

import com.jugo.smartwidget.R;

import android.app.Activity;
import android.os.Bundle;

public class SmartWidgetActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.calendar);
	}

}
