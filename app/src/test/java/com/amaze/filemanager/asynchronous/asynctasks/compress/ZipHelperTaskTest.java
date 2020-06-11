package com.amaze.filemanager.asynchronous.asynctasks.compress;

import android.os.Environment;
import java.io.File;
import org.robolectric.RuntimeEnvironment;

public class ZipHelperTaskTest extends AbstractCompressedHelperTaskTest {

protected CompressedHelperTask createTask(final String relativePath) {
	return new ZipHelperTask(
		RuntimeEnvironment.application,
		new File(Environment.getExternalStorageDirectory(), "test-archive.zip")
		.getAbsolutePath(),
		relativePath, false, (data)->{});
}
}
