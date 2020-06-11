package com.amaze.filemanager.asynchronous.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.widget.TextView;
import androidx.core.util.Pair;
import com.amaze.filemanager.R;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.amaze.filemanager.utils.files.FileUtils;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Emmanuel
 *         on 12/5/2017, at 19:40.
 */

public class CountItemsOrAndSizeTask
	extends AsyncTask<Void, Pair<Integer, Long>, String> {

private Context context;
private TextView itemsText;
private HybridFileParcelable file;
private boolean isStorage;

public CountItemsOrAndSizeTask(final Context c, final TextView itemsText,
                               final HybridFileParcelable f,
                               final boolean storage) {
	this.context = c;
	this.itemsText = itemsText;
	file = f;
	isStorage = storage;
}

@Override
protected String doInBackground(final Void[] params) {
	String items = "";
	long fileLength = file.length(context);

	if (file.isDirectory(context)) {
		final AtomicInteger x = new AtomicInteger(0);
		file.forEachChildrenFile(context, false, file->x.incrementAndGet());
		final int folderLength = x.intValue();
		long folderSize;

		if (isStorage) {
			folderSize = file.getUsableSpace();
		} else {
			folderSize = FileUtils.folderSize(
				file, data->publishProgress(new Pair<>(folderLength, data)));
		}

		items = getText(folderLength, folderSize, false);
	} else {
		items =
			Formatter.formatFileSize(context, fileLength) +
			(" (" + fileLength + " " +
			 context.getResources().getQuantityString(
				 R.plurals.bytes, (int)fileLength) // truncation is insignificant
			 + ")");
	}

	return items;
}

@Override
protected void onProgressUpdate(final Pair<Integer, Long>[] dataArr) {
	Pair<Integer, Long> data = dataArr[0];

	itemsText.setText(getText(data.first, data.second, true));
}

private String getText(final int filesInFolder, final long length,
                       final boolean loading) {
	String numOfItems = (filesInFolder != 0 ? filesInFolder + " " : "") +
	                    context.getResources().getQuantityString(
		R.plurals.items, filesInFolder);

	return numOfItems + "; " + (loading ? ">" : "") +
	       Formatter.formatFileSize(context, length);
}

protected void onPostExecute(final String items) {
	itemsText.setText(items);
}
}
