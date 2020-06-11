package com.amaze.filemanager.ui.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;
import com.amaze.filemanager.R;
import com.amaze.filemanager.activities.MainActivity;
import com.amaze.filemanager.asynchronous.services.ftp.FtpService;
import java.net.InetAddress;

/**
 * Created by yashwanthreddyg on 19-06-2016.
 *
 * Edited by zent-co on 30-07-2019
 */
public class FtpNotification extends BroadcastReceiver {

@Override
public void onReceive(final Context context, final Intent intent) {
	switch (intent.getAction()) {
	case FtpService.ACTION_STARTED:
		updateNotification(context, intent.getBooleanExtra(
					   FtpService.TAG_STARTED_BY_TILE, false));
		break;
	case FtpService.ACTION_STOPPED:
		removeNotification(context);
		break;
	}
}

private static NotificationCompat.Builder
buildNotification(final Context context, final @StringRes int contentTitleRes,
                  final String contentText, final boolean noStopButton) {
	Intent notificationIntent = new Intent(context, MainActivity.class);
	notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                            Intent.FLAG_ACTIVITY_SINGLE_TOP);
	PendingIntent contentIntent =
		PendingIntent.getActivity(context, 0, notificationIntent, 0);

	long when = System.currentTimeMillis();

	NotificationCompat.Builder builder =
		new NotificationCompat
		.Builder(context, NotificationConstants.CHANNEL_FTP_ID)
		.setContentTitle(context.getString(contentTitleRes))
		.setContentText(contentText)
		.setContentIntent(contentIntent)
		.setSmallIcon(R.drawable.ic_ftp_light)
		.setTicker(context.getString(R.string.ftp_notif_starting))
		.setWhen(when)
		.setOngoing(true)
		.setOnlyAlertOnce(true);

	if (!noStopButton) {
		int stopIcon = android.R.drawable.ic_menu_close_clear_cancel;
		CharSequence stopText = context.getString(R.string.ftp_notif_stop_server);
		Intent stopIntent = new Intent(FtpService.ACTION_STOP_FTPSERVER)
		                    .setPackage(context.getPackageName());
		PendingIntent stopPendingIntent = PendingIntent.getBroadcast(
			context, 0, stopIntent, PendingIntent.FLAG_ONE_SHOT);

		builder.addAction(stopIcon, stopText, stopPendingIntent);
	}

	NotificationConstants.setMetadata(context, builder,
	                                  NotificationConstants.TYPE_FTP);

	return builder;
}

public static Notification startNotification(final Context context,
                                             final boolean noStopButton) {
	NotificationCompat.Builder builder = buildNotification(
		context, R.string.ftp_notif_starting_title,
		context.getString(R.string.ftp_notif_starting), noStopButton);

	return builder.build();
}

private static void updateNotification(final Context context,
                                       final boolean noStopButton) {
	String notificationService = Context.NOTIFICATION_SERVICE;
	NotificationManager notificationManager =
		(NotificationManager)context.getSystemService(notificationService);

	SharedPreferences sharedPreferences =
		PreferenceManager.getDefaultSharedPreferences(context);
	int port = sharedPreferences.getInt(FtpService.PORT_PREFERENCE_KEY,
	                                    FtpService.DEFAULT_PORT);
	boolean secureConnection = sharedPreferences.getBoolean(
		FtpService.KEY_PREFERENCE_SECURE, FtpService.DEFAULT_SECURE);

	InetAddress address = FtpService.getLocalInetAddress(context);

	String iptext = (secureConnection ? FtpService.INITIALS_HOST_SFTP
	                              : FtpService.INITIALS_HOST_FTP) +
	                address.getHostAddress() + ":" + port + "/";

	NotificationCompat.Builder builder = buildNotification(
		context, R.string.ftp_notif_title,
		context.getString(R.string.ftp_notif_text, iptext), noStopButton);

	notificationManager.notify(NotificationConstants.FTP_ID, builder.build());
}

private static void removeNotification(final Context context) {
	String ns = Context.NOTIFICATION_SERVICE;
	NotificationManager nm = (NotificationManager)context.getSystemService(ns);
	nm.cancelAll();
}
}
