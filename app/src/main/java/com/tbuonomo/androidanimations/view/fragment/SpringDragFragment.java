package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.util.DragAndDropViewHelper;

/**
 * Created by tommy on 04/09/17.
 */

public class SpringDragFragment extends Fragment implements DragAndDropViewHelper.DragAndDropListener {
  public static final float MIN_DAMPING = 0.0f;
  public static final float MAX_DAMPING = 1.0f;
  private static final float MIN_STIFFNESS = 0.1f;
  private static final float MAX_STIFFNESS = 1500f;
  @BindView(R.id.spring_layout_drag)
  View dragLayout;
  @BindView(R.id.spring_ring_image)
  ImageView ringImageView;
  @BindView(R.id.damping_ratio_seekbar)
  SeekBar dampingRationSeekBar;
  @BindView(R.id.damping_ratio_text)
  TextView dampingRationTextView;
  @BindView(R.id.stiffness_seekbar)
  SeekBar stiffnessSeekBar;
  @BindView(R.id.stiffness_text)
  TextView stiffnessTextView;

  private SpringForce springForceTranslation;
  private SpringForce springForceScale;
  private SpringAnimation springAnimationX;
  private SpringAnimation springAnimationY;
  private SpringAnimation springRingAnimationScaleX;
  private SpringAnimation springRingAnimationScaleY;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_spring_drag, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    new DragAndDropViewHelper().setOnDragAndDropListener(this).applyOnView(dragLayout);

    springForceTranslation = new SpringForce(0f);
    springForceScale = new SpringForce();

    ringImageView.setScaleX(0);
    ringImageView.setScaleY(0);

    refreshSeekBars();

    stiffnessSeekBar.setProgress((int) (MIN_STIFFNESS + (springForceTranslation.getStiffness() / MAX_STIFFNESS) * 100));
    dampingRationSeekBar.setProgress((int) (MIN_DAMPING + (springForceTranslation.getDampingRatio() / MAX_DAMPING) * 100));

    dampingRationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        springForceTranslation.setDampingRatio(MIN_DAMPING + progress / 100f * (MAX_DAMPING - MIN_DAMPING));
        springForceScale.setDampingRatio(MIN_DAMPING + progress / 100f * (MAX_DAMPING - MIN_DAMPING));
        refreshSeekBars();
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    stiffnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        springForceTranslation.setStiffness(MIN_STIFFNESS + progress / 100f * (MAX_STIFFNESS - MIN_STIFFNESS));
        springForceScale.setStiffness(MIN_STIFFNESS + progress / 100f * (MAX_STIFFNESS - MIN_STIFFNESS));
        refreshSeekBars();
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    springRingAnimationScaleX = new SpringAnimation(ringImageView, DynamicAnimation.SCALE_X);
    springRingAnimationScaleX.setSpring(springForceScale);
    springRingAnimationScaleY = new SpringAnimation(ringImageView, DynamicAnimation.SCALE_Y);
    springRingAnimationScaleY.setSpring(springForceScale);
  }

  private void refreshSeekBars() {
    stiffnessTextView.setText(getString(R.string.stiffness, String.valueOf(springForceTranslation.getStiffness())));
    dampingRationTextView.setText(getString(R.string.damping_ratio, String.valueOf(springForceTranslation.getDampingRatio())));
  }

  @Override
  public void onDragEnd() {
    springAnimationX = new SpringAnimation(dragLayout, DynamicAnimation.TRANSLATION_X, 0);
    springAnimationY = new SpringAnimation(dragLayout, DynamicAnimation.TRANSLATION_Y, 0);

    springAnimationX.setSpring(springForceTranslation);
    springAnimationY.setSpring(springForceTranslation);

    springAnimationX.start();
    springAnimationY.start();

    springForceScale.setFinalPosition(0);
    if (!springRingAnimationScaleX.isRunning()) {
      springRingAnimationScaleX.start();
    }

    if (!springRingAnimationScaleY.isRunning()) {
      springRingAnimationScaleY.start();
    }
  }

  @Override
  public void onDragStart() {
    springForceScale.setFinalPosition(1);
    if (!springRingAnimationScaleX.isRunning()) {
      springRingAnimationScaleX.start();
    }

    if (!springRingAnimationScaleY.isRunning()) {
      springRingAnimationScaleY.start();
    }

    if (springAnimationX != null && springAnimationX.isRunning()) {
      springAnimationX.cancel();
    }

    if (springAnimationY != null && springAnimationY.isRunning()) {
      springAnimationY.cancel();
    }
  }
}
