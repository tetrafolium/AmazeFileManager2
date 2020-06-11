package com.amaze.filemanager.filesystem.usb;

import static android.os.Build.VERSION_CODES.KITKAT;
import static com.amaze.filemanager.filesystem.usb.ReflectionHelpers.addUsbOtgDevice;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.robolectric.Shadows.shadowOf;

import android.text.TextUtils;
import com.amaze.filemanager.BuildConfig;
import com.amaze.filemanager.activities.MainActivity;
import com.amaze.filemanager.utils.OTGUtil;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.multidex.ShadowMultiDex;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, shadows = {ShadowMultiDex.class})
public class UsbOtgTest {

@Test
@Config(minSdk = KITKAT)
public void usbConnectionTest() {
	ActivityController<MainActivity> controller =
		Robolectric.buildActivity(MainActivity.class).create();
	MainActivity activity = controller.get();

	addUsbOtgDevice(activity);

	activity = controller.resume().get();

	boolean hasOtgStorage = false;
	ArrayList<String> storageDirectories = activity.getStorageDirectories();
	for (String file : storageDirectories) {
		if (file.startsWith(OTGUtil.PREFIX_OTG)) {
			hasOtgStorage = true;
			break;
		}
	}

	assertTrue("No usb storage, known storages: '" +
	           TextUtils.join("', '", storageDirectories) + "'",
	           hasOtgStorage);
}
}
