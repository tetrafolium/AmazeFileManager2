package com.amaze.filemanager.utils.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amaze.filemanager.R;
import java.util.ArrayList;

/**
 * Created by Arpit on 01-07-2015 edited by Emmanuel Messulam
 * <emmanuelbendavid@gmail.com>
 */

class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {

  private ArrayList<Intent> items;
  private MaterialDialog dialog;
  private ArrayList<String> labels;
  private ArrayList<Drawable> drawables;
  private Context context;

  void updateMatDialog(final MaterialDialog b) { this.dialog = b; }

  ShareAdapter(final Context context, final ArrayList<Intent> intents,
               final ArrayList<String> labels,
               final ArrayList<Drawable> arrayList1) {
    items = new ArrayList<>(intents);
    this.context = context;
    this.labels = labels;
    this.drawables = arrayList1;
  }

  @Override
  public ViewHolder onCreateViewHolder(final ViewGroup parent,
                                       final int viewType) {
    View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.simplerow, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    holder.render(position);
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    private View rootView;

    private TextView textView;
    private ImageView imageView;

    ViewHolder(final View view) {
      super(view);

      rootView = view;

      textView = view.findViewById(R.id.firstline);
      imageView = view.findViewById(R.id.icon);
    }

    void render(final int position) {
      if (drawables.get(position) != null)
        imageView.setImageDrawable(drawables.get(position));
      textView.setVisibility(View.VISIBLE);
      textView.setText(labels.get(position));
      rootView.setOnClickListener(v -> {
        if (dialog != null && dialog.isShowing())
          dialog.dismiss();
        context.startActivity(items.get(position));
      });
    }
  }

  @Override
  public long getItemId(final int position) {
    return position;
  }

  @Override
  public int getItemCount() {
    return items.size();
  }
}
