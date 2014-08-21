package dev.jugo.smartwidget.activity;

import dev.jugo.smartwidget.R;

import android.app.Activity;
import android.os.Bundle;

public class CalendarActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.calendar);
	}
}
