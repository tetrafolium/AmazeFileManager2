package com.amaze.filemanager.filesystem.usb;

import static android.content.Context.USB_SERVICE;
import static android.hardware.usb.UsbConstants.USB_CLASS_MASS_STORAGE;
import static org.robolectric.Shadows.shadowOf;

import android.app.Activity;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.robolectric.shadows.ShadowEnvironment;
import org.robolectric.shadows.ShadowUsbManager;

class ReflectionHelpers {

  static void addUsbOtgDevice(final Activity activity) {
    ShadowUsbManager sUsbManager =
        shadowOf((UsbManager)activity.getSystemService(USB_SERVICE));

    UsbDevice device;
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          device = callUsbDeviceConstructor("usb", 0, 0, USB_CLASS_MASS_STORAGE,
                                            0, 0, null, null, "v2", null);
        } else {
          device = callUsbDeviceConstructor("usb", 0, 0, USB_CLASS_MASS_STORAGE,
                                            0, 0, null, null, null);
        }
        configureUsbDevice(device);
      } else {
        device = callUsbDeviceConstructor("usb", 0, 0, USB_CLASS_MASS_STORAGE,
                                          0, 0, configureUsbDevice());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    sUsbManager.addOrUpdateUsbDevice(device, true);

    File storageDir1 = new File("dir");
    ShadowEnvironment.setExternalStorageRemovable(storageDir1, true);
    ShadowEnvironment.setExternalStorageState(storageDir1,
                                              Environment.MEDIA_MOUNTED);
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  static void configureUsbDevice(final UsbDevice device)
      throws NoSuchMethodException, ClassNotFoundException,
             InvocationTargetException, InstantiationException,
             IllegalAccessException {
    UsbConfiguration usbConfiguration =
        callUsbConfigurationConstructor(0, "", 0, 0);
    configureUsbConfiguration(usbConfiguration);

    Method configureMethod = device.getClass().getDeclaredMethod(
        "setConfigurations", Parcelable[].class);
    configureMethod.invoke(device,
                           (Object) new Parcelable[] {usbConfiguration});
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  static void configureUsbConfiguration(final UsbConfiguration usbConfiguration)
      throws NoSuchMethodException, ClassNotFoundException,
             InvocationTargetException, InstantiationException,
             IllegalAccessException {
    UsbInterface usbInterface =
        callUsbInterfaceConstructor(01, 0, "", USB_CLASS_MASS_STORAGE, 0, 0);

    Method configureMethod = usbConfiguration.getClass().getDeclaredMethod(
        "setInterfaces", Parcelable[].class);
    configureMethod.invoke(usbConfiguration,
                           (Object) new Parcelable[] {usbInterface});
  }

  @RequiresApi(Build.VERSION_CODES.KITKAT)
  static Parcelable[] configureUsbDevice()
      throws ClassNotFoundException, NoSuchMethodException,
             InvocationTargetException, InstantiationException,
             IllegalAccessException {
    UsbInterface usbInterface = (UsbInterface)callUsbInterfaceConstructor(
        01, USB_CLASS_MASS_STORAGE, 0, 0, null);

    return new Parcelable[] {usbInterface};
  }

  @RequiresApi(Build.VERSION_CODES.M)
  static UsbDevice callUsbDeviceConstructor(
      final @NonNull String name, final int vendorId, final int productId,
      final int usbClass, final int subClass, final int protocol,
      final @Nullable String manufacturerName,
      final @Nullable String productName, final @NonNull String version,
      final @Nullable String serialNumber)
      throws ClassNotFoundException, NoSuchMethodException,
             IllegalAccessException, InvocationTargetException,
             InstantiationException {

    Class<UsbDevice> clazz =
        (Class<UsbDevice>)Class.forName("android.hardware.usb.UsbDevice");
    Constructor<UsbDevice> constructor = clazz.getConstructor(
        String.class, int.class, int.class, int.class, int.class, int.class,
        String.class, String.class, String.class, String.class);

    return constructor.newInstance(name, vendorId, productId, usbClass,
                                   subClass, protocol, manufacturerName,
                                   productName, version, serialNumber);
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  static UsbDevice callUsbDeviceConstructor(
      final @NonNull String name, final int vendorId, final int productId,
      final int usbClass, final int subClass, final int protocol,
      final @Nullable String manufacturerName,
      final @Nullable String productName, final @Nullable String serialNumber)
      throws ClassNotFoundException, NoSuchMethodException,
             IllegalAccessException, InvocationTargetException,
             InstantiationException {

    Class<UsbDevice> clazz =
        (Class<UsbDevice>)Class.forName("android.hardware.usb.UsbDevice");
    Constructor<UsbDevice> constructor = clazz.getConstructor(
        String.class, int.class, int.class, int.class, int.class, int.class,
        String.class, String.class, String.class);

    return constructor.newInstance(name, vendorId, productId, usbClass,
                                   subClass, protocol, manufacturerName,
                                   productName, serialNumber);
  }

  @RequiresApi(Build.VERSION_CODES.KITKAT)
  static UsbDevice
  callUsbDeviceConstructor(final @NonNull String name, final int vendorId,
                           final int productId, final int usbClass,
                           final int subClass, final int protocol,
                           final @NonNull Parcelable[] interfaces)
      throws ClassNotFoundException, NoSuchMethodException,
             IllegalAccessException, InvocationTargetException,
             InstantiationException {

    Class<UsbDevice> clazz =
        (Class<UsbDevice>)Class.forName("android.hardware.usb.UsbDevice");
    Constructor<UsbDevice> constructor =
        clazz.getConstructor(String.class, int.class, int.class, int.class,
                             int.class, int.class, Parcelable[].class);

    return constructor.newInstance(name, vendorId, productId, usbClass,
                                   subClass, protocol, interfaces);
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  static UsbConfiguration
  callUsbConfigurationConstructor(final int id, final @Nullable String name,
                                  final int attributes, final int maxPower)
      throws ClassNotFoundException, NoSuchMethodException,
             IllegalAccessException, InvocationTargetException,
             InstantiationException {

    Class<UsbConfiguration> clazz = (Class<UsbConfiguration>)Class.forName(
        "android.hardware.usb.UsbConfiguration");
    Constructor<UsbConfiguration> constructor =
        clazz.getConstructor(int.class, String.class, int.class, int.class);

    return constructor.newInstance(id, name, attributes, maxPower);
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  static UsbInterface
  callUsbInterfaceConstructor(final int id, final int alternateSetting,
                              final @Nullable String name, final int usbClass,
                              final int subClass, final int protocol)
      throws ClassNotFoundException, NoSuchMethodException,
             IllegalAccessException, InvocationTargetException,
             InstantiationException {

    Class<UsbInterface> clazz =
        (Class<UsbInterface>)Class.forName("android.hardware.usb.UsbInterface");
    Constructor<UsbInterface> constructor = clazz.getConstructor(
        int.class, int.class, String.class, int.class, int.class, int.class);

    return constructor.newInstance(id, alternateSetting, name, usbClass,
                                   subClass, protocol);
  }

  @RequiresApi(Build.VERSION_CODES.KITKAT)
  static UsbInterface
  callUsbInterfaceConstructor(final int id, final int usbClass,
                              final int subClass, final int protocol,
                              final @Nullable Parcelable[] endpoints)
      throws ClassNotFoundException, NoSuchMethodException,
             IllegalAccessException, InvocationTargetException,
             InstantiationException {

    Class<UsbInterface> clazz =
        (Class<UsbInterface>)Class.forName("android.hardware.usb.UsbInterface");
    Constructor<UsbInterface> constructor = clazz.getConstructor(
        int.class, int.class, int.class, int.class, Parcelable[].class);

    return constructor.newInstance(id, usbClass, subClass, protocol, endpoints);
  }
}
