package com.amaze.filemanager.asynchronous.asynctasks.compress;

import static org.junit.Assert.assertEquals;

import android.os.Environment;
import com.amaze.filemanager.adapters.data.CompressedObjectParcelable;
import com.amaze.filemanager.asynchronous.asynctasks.AsyncTaskResult;
import java.io.File;
import java.util.ArrayList;
import org.junit.Ignore;
import org.junit.Test;

public class SevenZipHelperTaskTest2 extends AbstractCompressedHelperTaskTest {

  @Test
  @Override
  public void testRoot() {
    CompressedHelperTask task = createTask("");
    AsyncTaskResult<ArrayList<CompressedObjectParcelable>> result =
        task.doInBackground();
    assertEquals(result.result.size(), 0);
  }

  @Test
  @Override
  @Ignore("Not testing this one")
  public void testSublevels() {}

  @Override
  protected CompressedHelperTask createTask(final String relativePath) {
    return new SevenZipHelperTask(
        new File(Environment.getExternalStorageDirectory(), "compress.7z")
            .getAbsolutePath(),
        relativePath, false, (data) -> {});
  }
}
