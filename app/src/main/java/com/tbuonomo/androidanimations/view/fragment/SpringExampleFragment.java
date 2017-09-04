package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.util.DragAndDropViewHelper;

/**
 * Created by tommy on 04/09/17.
 */

public class SpringExampleFragment extends Fragment implements DragAndDropViewHelper.DragAndDropListener {
  public static final float MIN_DAMPING = 0.0f;
  public static final float MAX_DAMPING = 1.0f;
  private static final float MIN_STIFFNESS = 0.1f;
  private static final float MAX_STIFFNESS = 1500f;
  @BindView(R.id.spring_card_view) CardView cardView;
  @BindView(R.id.damping_ratio_seekbar) SeekBar dampingRationSeekBar;
  @BindView(R.id.damping_ratio_text) TextView dampingRationTextView;
  @BindView(R.id.stiffness_seekbar) SeekBar stiffnessSeekBar;
  @BindView(R.id.stiffness_text) TextView stiffnessTextView;

  private SpringForce springForce;
  private SpringAnimation springAnimationX;
  private SpringAnimation springAnimationY;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.spring_fragment, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    new DragAndDropViewHelper().setOnDragAndDropListener(this).applyOnView(cardView);

    springForce = new SpringForce();
    springForce.setFinalPosition(0f);

    refreshSeekBars();

    stiffnessSeekBar.setProgress((int) (MIN_STIFFNESS + (springForce.getStiffness() / MAX_STIFFNESS) * 100));
    dampingRationSeekBar.setProgress((int) (MIN_DAMPING + (springForce.getDampingRatio() / MAX_DAMPING) * 100));

    dampingRationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        springForce.setDampingRatio(MIN_DAMPING + progress / 100f * (MAX_DAMPING - MIN_DAMPING));
        refreshSeekBars();
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    stiffnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        springForce.setStiffness(MIN_STIFFNESS + progress / 100f * (MAX_STIFFNESS - MIN_STIFFNESS));
        refreshSeekBars();
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
  }

  private void refreshSeekBars() {
    stiffnessTextView.setText(getString(R.string.stiffness, String.valueOf(springForce.getStiffness())));
    dampingRationTextView.setText(getString(R.string.damping_ratio, String.valueOf(springForce.getDampingRatio())));
  }

  @Override public void onDragEnd() {
    springAnimationX = new SpringAnimation(cardView, DynamicAnimation.TRANSLATION_X, 0);
    springAnimationY = new SpringAnimation(cardView, DynamicAnimation.TRANSLATION_Y, 0);

    springAnimationX.setSpring(springForce);
    springAnimationY.setSpring(springForce);

    springAnimationX.start();
    springAnimationY.start();
  }

  @Override public void onDragStart() {
    if (springAnimationX != null && springAnimationX.isRunning()) {
      springAnimationX.cancel();
    }

    if (springAnimationY != null && springAnimationY.isRunning()) {
      springAnimationY.cancel();
    }
  }
}
