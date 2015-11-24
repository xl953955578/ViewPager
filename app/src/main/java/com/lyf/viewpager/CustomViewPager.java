package com.lyf.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
	private GestureDetector gestureDetector;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void setGestureDetector(GestureDetector gestureDetector) {
		this.gestureDetector = gestureDetector;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (gestureDetector != null)
		{
			gestureDetector.onTouchEvent(event);
		}

		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		if (gestureDetector.onTouchEvent(event))
		{
			event.setAction(MotionEvent.ACTION_CANCEL);
		}

		return super.dispatchTouchEvent(event);
	}
	
}
