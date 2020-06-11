package com.amaze.filemanager.database.models;

/**
 * Created by vishal on 8/4/17.
 */

public class EncryptedEntry {

private int _id;
private String path, password;

public EncryptedEntry() {
}

public EncryptedEntry(final String path, final String password) {
	this.path = path;
	this.password = password;
}

public void setId(final int _id) {
	this._id = _id;
}

public int getId() {
	return this._id;
}

public void setPath(final String path) {
	this.path = path;
}

public String getPath() {
	return this.path;
}

public void setPassword(final String password) {
	this.password = password;
}

public String getPassword() {
	return this.password;
}
}
