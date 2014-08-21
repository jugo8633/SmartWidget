package dev.jugo.smartwidget.button;

import dev.jugo.smartwidget.R;

import dev.jugo.smartwidget.common.Device;
import dev.jugo.smartwidget.common.Type;
import dev.jugo.smartwidget.common.Utility;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public final class TabButton extends RelativeLayout
{
	private LinearLayout						linearLayout	= null;
	private ImageView							imageIndicate	= null;
	private SparseArray<Items>					listItem		= null;
	private float								mfX				= 0;
	private SparseArray<OnItemSwitchedListener>	listItemSwitch	= null;
	private int									mnSelectedId	= Type.INVALID;
	private Context								theContext		= null;
	private boolean								mbInit			= false;

	public static interface OnItemSwitchedListener
	{
		public void onItemSwitched(int nIndex);
	}

	private class Items
	{
		public TextView	mTextView	= null;
		public int		mnId		= Type.INVALID;

		public Items(TextView textView, int nId)
		{
			mTextView = textView;
			mnId = nId;
		}
	}

	public TabButton(Context context)
	{
		super(context);
		init(context);
	}

	public TabButton(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context);
	}

	public TabButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	private void init(Context context)
	{
		theContext = context;

		linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		this.addView(linearLayout);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Device.ScaleSize(context, 10),
				Device.ScaleSize(context, 10));
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		imageIndicate = new ImageView(context);
		imageIndicate.setLayoutParams(layoutParams);
		imageIndicate.setScaleType(ScaleType.CENTER_INSIDE);
		imageIndicate.setImageResource(R.drawable.triangle_indicate);
		this.addView(imageIndicate);
		imageIndicate.bringToFront();

		listItem = new SparseArray<Items>();
		listItemSwitch = new SparseArray<OnItemSwitchedListener>();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (!mbInit)
		{
			mbInit = true;
			setItemSelect(0);
		}
	}

	public void setDisplay(float fX, float fY, int nWidth, int nHeight)
	{
		this.setX(fX);
		this.setY(fY);
		this.setLayoutParams(new LayoutParams(nWidth, nHeight));
	}

	public void addTextButton(String strText)
	{
		TextView textView = new TextView(getContext());
		textView.setId(Utility.getUserId());
		textView.setText(strText);
		textView.setTextSize(18);
		textView.setTextColor(Color.GRAY);
		textView.setGravity(Gravity.CENTER);
		Rect bounds = new Rect();
		Paint textPaint = textView.getPaint();
		textPaint.getTextBounds(strText, 0, strText.length(), bounds);
		int width = bounds.width();
		textView.setLayoutParams(new LayoutParams(Device.ScaleSize(theContext, width), android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		linearLayout.addView(textView);
		listItem.put(listItem.size(), new Items(textView, textView.getId()));

		textView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				setItemSelect((TextView) view);
			}
		});
	}

	public void setItemSelect(int nIndex)
	{
		for (int i = 0; i < listItem.size(); ++i)
		{
			if (nIndex == i)
			{
				mnSelectedId = listItem.get(i).mnId;
				imageIndicate.clearAnimation();
				mfX = listItem.get(i).mTextView.getX();
				int nLeft = listItem.get(i).mTextView.getLeft();
				mfX = nLeft + (listItem.get(i).mTextView.getWidth() / 2) - (Device.ScaleSize(theContext, 10) / 2);
				imageIndicate.animate().translationX(mfX).setDuration(200)
						.setInterpolator(new AccelerateDecelerateInterpolator());
				listItem.get(i).mTextView.setTextColor(Color.BLUE);
				notifyItemSwitched(nIndex);
			}
			else
			{
				listItem.get(i).mTextView.setTextColor(Color.GRAY);
			}
		}
	}

	private void setItemSelect(TextView textView)
	{
		int nId = textView.getId();
		if (mnSelectedId == nId)
		{
			return;
		}
		mnSelectedId = nId;
		for (int i = 0; i < listItem.size(); ++i)
		{
			if (listItem.get(i).mnId == mnSelectedId)
			{
				setItemSelect(i);
				break;
			}
		}
	}

	public void setOnItemSwitchedListener(TabButton.OnItemSwitchedListener listener)
	{
		if (null != listener)
		{
			listItemSwitch.put(listItemSwitch.size(), listener);
		}
	}

	private void notifyItemSwitched(int nIndex)
	{
		for (int i = 0; i < listItemSwitch.size(); ++i)
		{
			listItemSwitch.get(i).onItemSwitched(nIndex);
		}
	}
}
