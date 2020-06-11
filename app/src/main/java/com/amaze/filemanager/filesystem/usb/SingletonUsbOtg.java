package com.amaze.filemanager.filesystem.usb;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SingletonUsbOtg {
    private static SingletonUsbOtg instance = null;

    public static SingletonUsbOtg getInstance() {
        if (instance == null) instance = new SingletonUsbOtg();
        return instance;
    }

    private UsbOtgRepresentation connectedDevice = null;
    private @Nullable Uri usbOtgRoot;

    private SingletonUsbOtg() { }

    public void setConnectedDevice(final UsbOtgRepresentation connectedDevice) {
        this.connectedDevice = connectedDevice;
    }

    public boolean isDeviceConnected() {
        return connectedDevice != null;
    }

    public void setUsbOtgRoot(final @Nullable Uri root) {
        if (connectedDevice == null) throw new IllegalStateException("No device connected!");
        usbOtgRoot = root;
    }

    public void resetUsbOtgRoot() {
        connectedDevice = null;
        usbOtgRoot = null;
    }

    public @Nullable Uri getUsbOtgRoot() {
        return usbOtgRoot;
    }

    public boolean checkIfRootIsFromDevice(final @NonNull UsbOtgRepresentation device) {
        return usbOtgRoot != null && connectedDevice.hashCode() == device.hashCode();
    }

}
