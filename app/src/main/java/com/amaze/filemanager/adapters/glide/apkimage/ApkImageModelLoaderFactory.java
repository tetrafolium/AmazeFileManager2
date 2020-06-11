package com.amaze.filemanager.adapters.glide.apkimage;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

/**
 * @author Emmanuel Messulam <emmanuelbendavid@gmail.com>
 *         on 10/12/2017, at 16:21.
 */

public class ApkImageModelLoaderFactory implements ModelLoaderFactory<String, Drawable> {

    private PackageManager packageManager;

    public ApkImageModelLoaderFactory(final PackageManager packageManager) {
        this.packageManager = packageManager;
    }

    @Override
    public ModelLoader<String, Drawable> build(final MultiModelLoaderFactory multiFactory) {
        return new ApkImageModelLoader(packageManager);
    }

    @Override
    public void teardown() {

    }
}
