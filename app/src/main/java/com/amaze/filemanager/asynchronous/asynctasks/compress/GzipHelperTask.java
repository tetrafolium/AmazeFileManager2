/*
 * GzipHelperTask.java
 *
 * Copyright (C) 2018-2019 Emmanuel Messulam<emmanuelbendavid@gmail.com>,
 * Raymond Lai <airwave209gt@gmail.com>.
 *
 * This file is part of Amaze File Manager.
 *
 * Amaze File Manager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.amaze.filemanager.asynchronous.asynctasks.compress;

import static com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR;

import android.content.Context;
import androidx.annotation.NonNull;
import com.amaze.filemanager.R;
import com.amaze.filemanager.adapters.data.CompressedObjectParcelable;
import com.amaze.filemanager.asynchronous.asynctasks.AsyncTaskResult;
import com.amaze.filemanager.filesystem.compressed.CompressedHelper;
import com.amaze.filemanager.utils.OnAsyncTaskFinished;
import com.amaze.filemanager.utils.application.AppConfig;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

public class GzipHelperTask extends CompressedHelperTask {

private WeakReference<Context> context;
private String filePath, relativePath;

public GzipHelperTask(
	final Context context, final String filePath, final String relativePath,
	final boolean goBack,
	final OnAsyncTaskFinished<
		AsyncTaskResult<ArrayList<CompressedObjectParcelable> > > l) {
	super(goBack, l);
	this.context = new WeakReference<>(context);
	this.filePath = filePath;
	this.relativePath = relativePath;
}

@Override
void addElements(final
                 @NonNull ArrayList<CompressedObjectParcelable> elements)
throws ArchiveException {
	TarArchiveInputStream tarInputStream = null;
	try {
		tarInputStream = new TarArchiveInputStream(
			new GzipCompressorInputStream(new FileInputStream(filePath)));

		TarArchiveEntry entry;
		while ((entry = tarInputStream.getNextTarEntry()) != null) {
			String name = entry.getName();
			if (!CompressedHelper.isEntryPathValid(name)) {
				AppConfig.toast(context.get(),
				                context.get().getString(
							R.string.multiple_invalid_archive_entries));
				continue;
			}
			if (name.endsWith(SEPARATOR))
				name = name.substring(0, name.length() - 1);

			boolean isInBaseDir =
				relativePath.equals("") && !name.contains(SEPARATOR);
			boolean isInRelativeDir =
				name.contains(SEPARATOR) &&
				name.substring(0, name.lastIndexOf(SEPARATOR)).equals(relativePath);

			if (isInBaseDir || isInRelativeDir) {
				elements.add(new CompressedObjectParcelable(
						     entry.getName(), entry.getLastModifiedDate().getTime(),
						     entry.getSize(), entry.isDirectory()));
			}
		}
	} catch (IOException e) {
		throw new ArchiveException(
			      String.format("Tarball archive %s is corrupt", filePath), e);
	}
}
}
