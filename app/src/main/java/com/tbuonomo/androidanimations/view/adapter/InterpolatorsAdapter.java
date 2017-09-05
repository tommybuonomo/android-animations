package com.tbuonomo.androidanimations.view.adapter;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;

/**
 * Created by ebiz on 05/09/17.
 */

public class InterpolatorsAdapter extends RecyclerView.Adapter<InterpolatorsAdapter.ViewHolder> {

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interpolator, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    InterpolatorItem interpolatorItem = InterpolatorItem.values()[position];
    holder.title.setText(interpolatorItem.title);

    holder.itemView.setOnClickListener(v -> {
      ObjectAnimator animator = ObjectAnimator.ofFloat(holder.animatedView, View.TRANSLATION_X, 0);
    });
  }

  @Override public int getItemCount() {
    return InterpolatorItem.values().length;
  }

  public enum InterpolatorItem {
    LINEAR(R.string.linear_interpolator, new LinearInterpolator()), ACCELERATE(R.string.accelerate_interpolator, new AccelerateInterpolator());

    private final Interpolator interpolator;
    private final int title;

    InterpolatorItem(int title, Interpolator interpolator) {
      this.title = title;
      this.interpolator = interpolator;
    }
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_interpolator_animated_view) View animatedView;
    @BindView(R.id.item_interpolator_title) TextView title;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
