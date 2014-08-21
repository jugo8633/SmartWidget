package dev.jugo.smartwidgettester;

import dev.jugo.smartwidget.button.TabButton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity
{

	private TabButton	tabButton	= null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initTabButton();
	}

	private void initTabButton()
	{
		tabButton = (TabButton) this.findViewById(R.id.tabButton1);
		tabButton.addTextButton(this.getString(R.string.test_1));
		tabButton.addTextButton(this.getString(R.string.test_2));
		tabButton.addTextButton(this.getString(R.string.test_3));
		tabButton.addTextButton(this.getString(R.string.test_4));
		tabButton.setItemSelect(0);
		tabButton.setOnItemSwitchedListener(new TabButton.OnItemSwitchedListener()
		{
			@Override
			public void onItemSwitched(int nIndex)
			{
				String strMsg = String.format("Select Tab: %d", nIndex);
				Toast.makeText(MainActivity.this, strMsg, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
