package com.jugo.smartwidget.activity;

import com.jugo.smartwidget.R;
import com.jugo.smartwidget.button.TabButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SmartWidgetActivity extends Activity
{

	private TabButton	tabButton	= null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.smart_widget);

		tabButton = (TabButton) this.findViewById(R.id.tabButton1);
		tabButton.addTextButton("測試一");
		tabButton.addTextButton("測試二");
		tabButton.addTextButton("測試三");
		tabButton.addTextButton("測試四");
		tabButton.setItemSelect(0);
		tabButton.setOnItemSwitchedListener(new TabButton.OnItemSwitchedListener()
		{
			@Override
			public void onItemSwitched(int nIndex)
			{
				Toast.makeText(SmartWidgetActivity.this, "Select Tab " + String.valueOf(nIndex), Toast.LENGTH_LONG).show();
			}
		});

		Button btnCalendar = (Button) this.findViewById(R.id.buttonCalendar);
		btnCalendar.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(SmartWidgetActivity.this, CalendarActivity.class);
				startActivity(intent); 
			}
		});
	}

}
