package com.amaze.filemanager.ui.views.preference;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;
import com.amaze.filemanager.utils.PreferenceUtils;

/**
 * @author Emmanuel
 *         on 15/10/2017, at 20:46.
 */

public class InvalidablePreferenceCategory extends PreferenceCategory {

private int titleColor;

public InvalidablePreferenceCategory(final Context context,
                                     final AttributeSet attrs) {
	super(context, attrs);
}

@Override
protected void onBindView(final View view) {
	super.onBindView(view);
	AppCompatTextView title = view.findViewById(android.R.id.title);
	title.setTextColor(titleColor);
}

public void invalidate(final @ColorInt int accentColor) {
	titleColor = PreferenceUtils.getStatusColor(accentColor);
	notifyChanged();
}
}
