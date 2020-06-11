package com.amaze.filemanager.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtils {

public static final int TOOLBAR_HEIGHT_IN_DP = 128;   // 160 dpi

private Activity activity;

public ScreenUtils(final Activity activity) {
	this.activity = activity;
}

public int convertDbToPx(final float dp) {
	return Math.round(activity.getResources().getDisplayMetrics().density * dp);
}

public int convertPxToDb(final float px) {
	return Math.round(px / activity.getResources().getDisplayMetrics().density);
}

public int getScreenWidthInPx() {
	DisplayMetrics displayMetrics = new DisplayMetrics();
	activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
	return displayMetrics.widthPixels;
}

public int getScreenHeightInPx() {
	DisplayMetrics displayMetrics = new DisplayMetrics();
	activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
	return displayMetrics.heightPixels;
}

public int getScreenWidthInDp() {
	return convertPxToDb(getScreenWidthInPx());
}

public int getScreeHeightInDb() {
	return convertPxToDb(getScreenHeightInPx());
}
}
