package com.amaze.filemanager.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.amaze.filemanager.R;

/**
 * This is a circle that can have a check (√) in the middle
 */
public class CheckableCircleView extends View {

private static final float CHECK_MARGIN_PERCENTUAL = 0.15f;

private Drawable check;
private Paint paint = new Paint();
private boolean checked;

public CheckableCircleView(final Context context,
                           final @Nullable AttributeSet attrs) {
	super(context, attrs);
	check = context.getResources().getDrawable(R.drawable.ic_check_white_24dp);
}

public void setColor(final @ColorInt int color) {
	paint.setColor(color);
	paint.setAntiAlias(true);
	invalidate();
}

public void setChecked(final boolean checked) {
	this.checked = checked;
	invalidate();
}

@Override
protected void onDraw(final Canvas canvas) {
	super.onDraw(canvas);

	float min = Math.min(getHeight(), getWidth());

	canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, min / 2f, paint);

	if (checked) {
		float checkMargin = min * CHECK_MARGIN_PERCENTUAL;
		check.setBounds((int)checkMargin, (int)checkMargin,
		                (int)(getWidth() - checkMargin),
		                (int)(getHeight() - checkMargin));
		check.draw(canvas);
	}
}
}
