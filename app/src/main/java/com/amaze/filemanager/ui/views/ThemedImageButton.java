package com.amaze.filemanager.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * @author Emmanuel
 *         on 8/5/2017, at 13:39.
 */

public class ThemedImageButton extends ThemedImageView {

public ThemedImageButton(final Context context) {
	super(context);
}

public ThemedImageButton(final Context context, final AttributeSet attrs) {
	super(context, attrs);
}

public ThemedImageButton(final Context context, final AttributeSet attrs,
                         final int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	setFocusable(true);
}

@Override
protected boolean onSetAlpha(final int alpha) {
	return false;
}

@Override
public CharSequence getAccessibilityClassName() {
	return ImageButton.class.getName();
}
}
