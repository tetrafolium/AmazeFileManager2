package com.amaze.filemanager.asynchronous.asynctasks.compress;

import android.os.Environment;
import java.io.File;
import org.robolectric.RuntimeEnvironment;

public class TarGzHelperTaskTest extends AbstractCompressedHelperTaskTest {

@Override
protected CompressedHelperTask createTask(final String relativePath) {
	return new GzipHelperTask(
		RuntimeEnvironment.application,
		new File(Environment.getExternalStorageDirectory(),
		         "test-archive.tar.gz")
		.getAbsolutePath(),
		relativePath, false, (data)->{});
}
}
