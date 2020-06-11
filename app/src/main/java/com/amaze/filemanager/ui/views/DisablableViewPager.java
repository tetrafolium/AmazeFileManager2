package com.amaze.filemanager.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Arpit on 16-01-2015.
 */
public class DisablableViewPager extends ViewPager {

  private boolean enabled;

  public DisablableViewPager(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    this.enabled = true;
  }

  @Override
  public boolean onTouchEvent(final MotionEvent event) {
    return this.enabled && super.onTouchEvent(event);
  }

  @Override
  public boolean onInterceptTouchEvent(final MotionEvent event) {
    return this.enabled && super.onInterceptTouchEvent(event);
  }

  public void setPagingEnabled(final boolean enabled) {
    this.enabled = enabled;
  }
}
