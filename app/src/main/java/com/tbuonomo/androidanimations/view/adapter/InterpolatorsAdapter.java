package com.tbuonomo.androidanimations.view.adapter;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.util.DimenUtils;

/**
 * Created by ebiz on 05/09/17.
 */

public class InterpolatorsAdapter extends RecyclerView.Adapter<InterpolatorsAdapter.ViewHolder> {

  private Context context;

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interpolator, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    InterpolatorItem interpolatorItem = InterpolatorItem.values()[position];
    holder.title.setText(interpolatorItem.title);

    holder.itemView.setOnClickListener(v -> {

      // Translate X animator
      float toX = holder.layout.getWidth() - 2 * DimenUtils.toDp(context, 24) - holder.animatedView.getWidth();
      ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(holder.animatedView, View.TRANSLATION_X, 0, toX);
      translateAnimator.setDuration(2000);
      translateAnimator.setInterpolator(interpolatorItem.interpolator);
      translateAnimator.start();

      // Translate scale animator
      PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1, 0.7f, 1);
      PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1, 0.7f, 1);

      ObjectAnimator scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(holder.animatedView, scaleX, scaleY);
      scaleAnimator.setDuration(2000);
      scaleAnimator.setInterpolator(interpolatorItem.interpolator);
      scaleAnimator.start();
    });
  }

  @Override public int getItemCount() {
    return InterpolatorItem.values().length;
  }

  public enum InterpolatorItem {
    LINEAR(R.string.linear_interpolator, new LinearInterpolator()),
    ACCELERATE(R.string.accelerate_interpolator, new AccelerateInterpolator()),
    DECELERATE(R.string.decelerate_interpolator, new DecelerateInterpolator()),
    ACCELERATE_DECELERATE(R.string.accelerate_decelerate_interpolator, new AccelerateDecelerateInterpolator()),
    ANTICIPATE(R.string.anticipate_interpolator, new AnticipateInterpolator()),
    ANTICIPATE_OVERSHOOT(R.string.anticipate_overshoot_interpolator, new AnticipateOvershootInterpolator()),
    OVERSHOOT(R.string.overshoot_interpolator, new OvershootInterpolator()),
    BOUNCE(R.string.bounce_interpolator, new BounceInterpolator()),
    FAST_OUT_LINEAR_IN(R.string.fast_out_linear_in_interpolator, new FastOutLinearInInterpolator()),
    FAST_OUT_SLOW_IN(R.string.fast_out_slow_in_interpolator, new FastOutSlowInInterpolator()),
    LINEAR_OUT_SLOW_IN(R.string.linear_out_slow_in_interpolator, new LinearOutSlowInInterpolator()),
    CYCLE(R.string.cycle_interpolator, new CycleInterpolator(2));

    private final Interpolator interpolator;
    private final int title;

    InterpolatorItem(int title, Interpolator interpolator) {
      this.title = title;
      this.interpolator = interpolator;
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_interpolator_animated_view) View animatedView;
    @BindView(R.id.item_interpolator_title) TextView title;
    @BindView(R.id.item_interpolator_layout) View layout;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
