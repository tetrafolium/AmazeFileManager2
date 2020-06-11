/*
 * RarDecompressor.java
 *
 * Copyright (C) 2017-2018 Emmanuel Messulam<emmanuelbendavid@gmail.com>,
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

package com.amaze.filemanager.filesystem.compressed.showcontents.helpers;

import static com.amaze.filemanager.filesystem.compressed.CompressedHelper.SEPARATOR;

import android.content.Context;
import com.amaze.filemanager.adapters.data.CompressedObjectParcelable;
import com.amaze.filemanager.asynchronous.asynctasks.AsyncTaskResult;
import com.amaze.filemanager.asynchronous.asynctasks.compress.RarHelperTask;
import com.amaze.filemanager.filesystem.compressed.showcontents.Decompressor;
import com.amaze.filemanager.utils.OnAsyncTaskFinished;
import com.github.junrar.rarfile.FileHeader;
import java.util.ArrayList;

public class RarDecompressor extends Decompressor {

public RarDecompressor(final Context context) {
	super(context);
}

@Override
public RarHelperTask changePath(
	final String path, final boolean addGoBackItem,
	final OnAsyncTaskFinished<
		AsyncTaskResult<ArrayList<CompressedObjectParcelable> > > onFinish) {
	return new RarHelperTask(filePath, path, addGoBackItem, onFinish);
}

public static String convertName(final FileHeader file) {
	String name = file.getFileNameString().replace('\\', '/');

	if (file.isDirectory())
		return name + SEPARATOR;
	else
		return name;
}

@Override
protected String realRelativeDirectory(final String dir) {
	if (dir.endsWith(SEPARATOR))
		dir = dir.substring(0, dir.length() - 1);
	return dir.replace(SEPARATOR.toCharArray()[0], '\\');
}
}
