package com.amaze.filemanager.ui.colors;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.ColorInt;

public final class UserColorPreferences implements Parcelable {

  public final @ColorInt int primaryFirstTab, primarySecondTab, accent,
      iconSkin;

  public UserColorPreferences(final @ColorInt int primaryFirstTab,
                              final @ColorInt int primarySecondTab,
                              final @ColorInt int accent,
                              final @ColorInt int iconSkin) {
    this.primaryFirstTab = primaryFirstTab;
    this.primarySecondTab = primarySecondTab;
    this.accent = accent;
    this.iconSkin = iconSkin;
  }

  private UserColorPreferences(final Parcel in) {
    primaryFirstTab = in.readInt();
    primarySecondTab = in.readInt();
    accent = in.readInt();
    iconSkin = in.readInt();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    dest.writeInt(primaryFirstTab);
    dest.writeInt(primarySecondTab);
    dest.writeInt(accent);
    dest.writeInt(iconSkin);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<UserColorPreferences> CREATOR =
      new Creator<UserColorPreferences>() {
        @Override
        public UserColorPreferences createFromParcel(final Parcel in) {
          return new UserColorPreferences(in);
        }

        @Override
        public UserColorPreferences[] newArray(final int size) {
          return new UserColorPreferences[size];
        }
      };
}
