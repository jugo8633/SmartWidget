package com.jugo.smartwidget.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridCellAdapter extends BaseAdapter
{
	public static final int			DAY_SELECTED	= 0;

	private final int				DAY_OFFSET		= 1;
	private Context					theContext		= null;
	public static final String[]	weekdays		= new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	public final String[]			months			= { "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };
	private final int[]				daysOfMonth		= { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private SparseArray<Day>		listDay			= null;
	private int						daysInMonth		= 0;
	private int						currentDayOfMonth;
	private int						currentWeekDay;
	private boolean					mbShowToday		= false;
	private Handler					theHandler		= null;

	public class Day
	{
		public String	mstrDay;
		public int		mnColor;
		public String	mstrMonth;
		public int		mnYear;

		public Day(String strDay, int nColor, String strMonth, int nYear)
		{
			mstrDay = strDay;
			mnColor = nColor;
			mstrMonth = strMonth;
			mnYear = nYear;
		}
	}

	public GridCellAdapter(Context context, Handler handler, int month, int year)
	{
		super();
		theHandler = handler;
		theContext = context;
		listDay = new SparseArray<Day>();
		Calendar calendar = Calendar.getInstance();
		setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
		setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		printMonth(month, year);
	}

	@Override
	public int getCount()
	{
		return listDay.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView tvDay = new TextView(theContext);
		Day day = listDay.get(position);
		tvDay.setText(day.mstrDay);
		tvDay.setTextColor(day.mnColor);
		tvDay.setBackgroundColor(Color.WHITE);
		tvDay.setGravity(Gravity.CENTER);
		tvDay.setTextSize(32);
		tvDay.setPadding(1, 10, 1, 10);
		tvDay.setLayoutParams(new AbsListView.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT)));

		tvDay.setOnClickListener(clickListener);
		return tvDay;
	}

	private String getMonthAsString(int i)
	{
		return months[i];
	}

	private int getNumberOfDaysOfMonth(int i)
	{
		return daysOfMonth[i];
	}

	public int getCurrentDayOfMonth()
	{
		return currentDayOfMonth;
	}

	private void setCurrentDayOfMonth(int currentDayOfMonth)
	{
		this.currentDayOfMonth = currentDayOfMonth;
	}

	public void setCurrentWeekDay(int currentWeekDay)
	{
		this.currentWeekDay = currentWeekDay;
	}

	public int getCurrentWeekDay()
	{
		return currentWeekDay;
	}

	private void printMonth(int mm, int yy)
	{
		int trailingSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int prevYear = 0;
		int nextMonth = 0;
		int nextYear = 0;

		int currentMonth = mm - 1;
		daysInMonth = getNumberOfDaysOfMonth(currentMonth);

		GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

		if (currentMonth == 11)
		{
			prevMonth = currentMonth - 1;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 0;
			prevYear = yy;
			nextYear = yy + 1;
		}
		else if (currentMonth == 0)
		{
			prevMonth = 11;
			prevYear = yy - 1;
			nextYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 1;
		}
		else
		{
			prevMonth = currentMonth - 1;
			nextMonth = currentMonth + 1;
			nextYear = yy;
			prevYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
		}

		int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		trailingSpaces = currentWeekDay;

		if (cal.isLeapYear(cal.get(Calendar.YEAR)))
			if (mm == 2)
				++daysInMonth;
			else if (mm == 3)
				++daysInPrevMonth;

		// Trailing Month days
		for (int i = 0; i < trailingSpaces; ++i)
		{
			listDay.append(listDay.size(), new Day(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i),
					Color.GRAY, getMonthAsString(prevMonth), prevYear));
		}

		// Current Month Days
		for (int i = 1; i <= daysInMonth; ++i)
		{

			if (i == getCurrentDayOfMonth())
			{
				if (mbShowToday)
				{
					listDay.append(listDay.size(), new Day(String.valueOf(i), Color.BLUE,
							getMonthAsString(currentMonth), yy));
				}
				else
				{
					listDay.append(listDay.size(), new Day(String.valueOf(i), Color.BLACK,
							getMonthAsString(currentMonth), yy));
				}
			}
			else
			{
				listDay.append(listDay.size(), new Day(String.valueOf(i), Color.BLACK, getMonthAsString(currentMonth),
						yy));
			}
		}

		// Leading Month days
		for (int i = 0; i < listDay.size() % 7; ++i)
		{
			listDay.append(listDay.size(), new Day(String.valueOf(i + 1), Color.GRAY, getMonthAsString(nextMonth),
					nextYear));
		}
	}

	private OnClickListener	clickListener	= new OnClickListener()
											{
												@Override
												public void onClick(View v)
												{

												}
											};
}
