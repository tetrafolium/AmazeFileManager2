package com.amaze.filemanager.database.models;

import static com.amaze.filemanager.database.UtilsHandler.Operation.BOOKMARKS;
import static com.amaze.filemanager.database.UtilsHandler.Operation.GRID;
import static com.amaze.filemanager.database.UtilsHandler.Operation.HIDDEN;
import static com.amaze.filemanager.database.UtilsHandler.Operation.HISTORY;
import static com.amaze.filemanager.database.UtilsHandler.Operation.LIST;
import static com.amaze.filemanager.database.UtilsHandler.Operation.SFTP;
import static com.amaze.filemanager.database.UtilsHandler.Operation.SMB;

import android.text.TextUtils;
import com.amaze.filemanager.database.UtilsHandler;
import com.amaze.filemanager.database.UtilsHandler.Operation;

public class OperationData {
public final Operation type;
public final String path;
public final String name;
public final String hostKey;
public final String sshKeyName;
public final String sshKey;

/**
 * Constructor for types {@link Operation#HIDDEN}, {@link Operation#HISTORY},
 * {@link Operation#LIST} or {@link Operation#GRID}
 */
public OperationData(final Operation type, final String path) {
	if (type != HIDDEN && type != HISTORY && type != LIST && type != GRID) {
		throw new IllegalArgumentException("Wrong constructor for object type");
	}

	this.type = type;
	this.path = path;

	name = null;
	hostKey = null;
	sshKeyName = null;
	sshKey = null;
}

/**
 * Constructor for types {@link Operation#BOOKMARKS} or {@link Operation#SMB}
 */
public OperationData(final Operation type, final String name,
                     final String path) {
	if (type != BOOKMARKS && type != SMB)
		throw new IllegalArgumentException("Wrong constructor for object type");

	this.type = type;
	this.path = path;
	this.name = name;

	hostKey = null;
	sshKeyName = null;
	sshKey = null;
}

/**
 * Constructor for {@link Operation#SFTP}
 * {@param hostKey}, {@param sshKeyName} and {@param sshKey} may be null for
 * when
 * {@link OperationData} is used for {@link
 * UtilsHandler#removeFromDatabase(OperationData)}
 */
public OperationData(final Operation type, final String path,
                     final String name, final String hostKey,
                     final String sshKeyName, final String sshKey) {
	if (type != SFTP)
		throw new IllegalArgumentException("Wrong constructor for object type");

	this.type = type;
	this.path = path;
	this.name = name;
	this.hostKey = hostKey;
	this.sshKeyName = sshKeyName;
	this.sshKey = sshKey;
}

@Override
public String toString() {
	StringBuilder sb = new StringBuilder("OperationData type=[")
	                   .append(type)
	                   .append("],path=[")
	                   .append(path)
	                   .append("]");

	if (!TextUtils.isEmpty(hostKey))
		sb.append(",hostKey=[").append(hostKey).append(']');

	if (!TextUtils.isEmpty(sshKeyName))
		sb.append(",sshKeyName=[")
		.append(sshKeyName)
		.append("],sshKey=[redacted]");

	return sb.toString();
}
}
