package com.amaze.filemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import com.amaze.filemanager.R;
import com.amaze.filemanager.ui.views.CheckableCircleView;
import com.amaze.filemanager.utils.Utils;

public class ColorAdapter
    extends ArrayAdapter<Integer> implements AdapterView.OnItemClickListener {

  private LayoutInflater inflater;
  private @ColorInt int selectedColor;
  private OnColorSelected onColorSelected;

  /**
   * Constructor for adapter that handles the view creation of color chooser
   * dialog in preferences
   *
   * @param context the context
   * @param colors  array list of color hex values in form of string; for the
   *     views
   * @param selectedColor currently selected color
   * @param l OnColorSelected listener for when a color is selected
   */
  public ColorAdapter(final Context context, final Integer[] colors,
                      final @ColorInt int selectedColor,
                      final OnColorSelected l) {
    super(context, R.layout.rowlayout, colors);
    this.selectedColor = selectedColor;
    this.onColorSelected = l;

    inflater = (LayoutInflater)getContext().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
  }

  @ColorRes
  private int getColorResAt(final int position) {
    return getItem(position);
  }

  @NonNull
  @Override
  public View getView(final int position, final View convertView,
                      final @NonNull ViewGroup parent) {
    CheckableCircleView colorView;
    if (convertView != null && convertView instanceof CheckableCircleView) {
      colorView = (CheckableCircleView)convertView;
    } else {
      colorView = (CheckableCircleView)inflater.inflate(
          R.layout.dialog_grid_item, parent, false);
    }

    @ColorInt int color = Utils.getColor(getContext(), getColorResAt(position));

    colorView.setChecked(color == selectedColor);
    colorView.setColor(color);

    return colorView;
  }

  @Override
  public void onItemClick(final AdapterView<?> parent, final View view,
                          final int position, final long id) {
    this.selectedColor = Utils.getColor(getContext(), getColorResAt(position));
    ((CheckableCircleView)view).setChecked(true);
    onColorSelected.onColorSelected(this.selectedColor);
  }

  public interface OnColorSelected {
    void onColorSelected(@ColorInt int color);
  }
}
