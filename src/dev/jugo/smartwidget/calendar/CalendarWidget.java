package dev.jugo.smartwidget.calendar;

import java.util.Calendar;
import java.util.Locale;

import dev.jugo.smartwidget.R;

import dev.jugo.smartwidget.common.Logs;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarWidget extends RelativeLayout
{
	private LinearLayout			llMain					= null;
	private LinearLayout			llMonthYear				= null;
	private LinearLayout			llCalendarDays			= null;
	private GridView				gvCalendar				= null;
	private ImageView				ivArrowLeft				= null;
	private ImageView				ivArrowRight			= null;
	private ImageView				ivCalendarDays			= null;
	private TextView				tvMonthYear				= null;
	private GridCellAdapter			adapter					= null;
	private static final String		dateTemplate			= "MMMM yyyy";
	private int						month, year;
	private Calendar				_calendar;
	private Context					theContext				= null;
	private OnDaySelectedListener	onDaySelectedListener	= null;

	public static interface OnDaySelectedListener
	{
		void onDaySelected(int nDay);
	}

	public void setOnDaySelectedListener(OnDaySelectedListener listener)
	{
		if (null != listener)
		{
			onDaySelectedListener = listener;
		}
	}

	public CalendarWidget(Context context)
	{
		super(context);
		init(context);
	}

	public CalendarWidget(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public CalendarWidget(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context)
	{
		theContext = context;
		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.1f);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
		LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.08f);

		llMain = new LinearLayout(context);
		llMain.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		llMain.setOrientation(LinearLayout.VERTICAL);

		llMonthYear = new LinearLayout(context);
		try
		{
			llMonthYear.setBackgroundResource(R.drawable.calendar_bg_orange);
		}
		catch(Exception e)
		{
			
		}
		llMonthYear.setPadding(20, 2, 20, 2);

		llCalendarDays = new LinearLayout(context);
		llCalendarDays.setBackgroundColor(Color.LTGRAY);
		llCalendarDays.setOrientation(LinearLayout.HORIZONTAL);
		TextView tvCalDay = null;
		for (int i = 0; i < 7; ++i)
		{
			tvCalDay = new TextView(context);
			tvCalDay.setText(GridCellAdapter.weekdays[i]);
			tvCalDay.setGravity(Gravity.CENTER);
			tvCalDay.setTextColor(Color.BLACK);
			llCalendarDays.addView(tvCalDay, params3);
		}

		gvCalendar = new GridView(context);
		gvCalendar.setBackgroundColor(Color.TRANSPARENT);
		gvCalendar.setNumColumns(7);
		gvCalendar.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		gvCalendar.setHorizontalSpacing(3);
		gvCalendar.setVerticalSpacing(3);

		ivArrowLeft = new ImageView(context);
		ivArrowLeft.setLayoutParams(new LayoutParams(60, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		ivArrowLeft.setImageResource(R.drawable.cal_left_arrow_on);
		ivArrowLeft.setScaleType(ScaleType.FIT_XY);
		ivArrowLeft.setAdjustViewBounds(true);
		ivArrowLeft.setOnClickListener(onClickListener);
		ivArrowRight = new ImageView(context);
		ivArrowRight.setLayoutParams(new LayoutParams(60, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		ivArrowRight.setImageResource(R.drawable.cal_right_arrow_on);
		ivArrowRight.setScaleType(ScaleType.FIT_XY);
		ivArrowRight.setAdjustViewBounds(true);
		ivArrowRight.setOnClickListener(onClickListener);
		ivCalendarDays = new ImageView(context);
		ivCalendarDays.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		ivCalendarDays.setScaleType(ScaleType.FIT_XY);
		ivCalendarDays.setAdjustViewBounds(true);
		ivCalendarDays.setImageResource(R.drawable.calendar_days);

		tvMonthYear = new TextView(context);
		tvMonthYear.setPadding(20, 0, 20, 0);
		tvMonthYear.setTextColor(Color.WHITE);
		tvMonthYear.setTextSize(32);
		tvMonthYear.setText(DateFormat.format(dateTemplate, Calendar.getInstance(Locale.getDefault()).getTime()));
		tvMonthYear.setGravity(Gravity.CENTER);

		llMonthYear.addView(ivArrowLeft);
		llMonthYear.addView(tvMonthYear, params3);
		llMonthYear.addView(ivArrowRight);

		llMain.addView(llMonthYear, params);
		llMain.addView(llCalendarDays, params4);
		llMain.addView(gvCalendar, params2);
		this.addView(llMain);

		setGridCellAdapterToDate(context, gvCalendar, month, year);
	}

	private void setGridCellAdapterToDate(Context context, GridView gridView, int month, int year)
	{
		adapter = null;
		adapter = new GridCellAdapter(context, selfHandler, month, year);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		tvMonthYear.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
		adapter.notifyDataSetChanged();
		gridView.setAdapter(adapter);
	}

	public void showMonthYear(boolean bShow)
	{
		if (bShow)
		{
			llMonthYear.setVisibility(View.VISIBLE);
		}
		else
		{
			llMonthYear.setVisibility(View.GONE);
		}
	}

	public void showWeekDay(boolean bShow)
	{
		if (bShow)
		{
			llCalendarDays.setVisibility(View.VISIBLE);
		}
		else
		{
			llCalendarDays.setVisibility(View.GONE);
		}
	}

	private OnClickListener	onClickListener	= new OnClickListener()
											{
												@Override
												public void onClick(View v)
												{
													if (v == ivArrowLeft)
													{
														if (month <= 1)
														{
															month = 12;
															--year;
														}
														else
														{
															--month;
														}
														setGridCellAdapterToDate(theContext, gvCalendar, month, year);
													}
													if (v == ivArrowRight)
													{
														if (month > 11)
														{
															month = 1;
															++year;
														}
														else
														{
															++month;
														}
														setGridCellAdapterToDate(theContext, gvCalendar, month, year);
													}
												}
											};

	private Handler			selfHandler		= new Handler()
											{
												@Override
												public void handleMessage(Message msg)
												{
													switch (msg.what)
													{
													case GridCellAdapter.DAY_SELECTED:
														if (null != onDaySelectedListener)
														{
															onDaySelectedListener.onDaySelected(msg.arg1);
														}
														Logs.showTrace("Day select: " + msg.arg1);
														break;
													}
												}
											};
}
