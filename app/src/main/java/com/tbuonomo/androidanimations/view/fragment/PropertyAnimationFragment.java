package com.tbuonomo.androidanimations.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.util.DimenUtils;

/**
 * Created by ebiz on 10/10/17.
 */

public class PropertyAnimationFragment extends Fragment {
  @BindView(R.id.property_card_view) CardView cardView;
  @BindView(R.id.property_position_button) Button positionButton;
  @BindView(R.id.property_rotation_button) Button rotationButton;
  @BindView(R.id.property_alpha_button) Button alphaButton;
  @BindView(R.id.property_size_button) Button sizeButton;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_property_animation, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @OnClick(R.id.property_position_button) public void onPositionButtonClick() {
    ObjectAnimator startTranslateAnimator = ObjectAnimator.ofFloat(cardView, View.TRANSLATION_Y, 0, DimenUtils.toDp(getContext(), 100));
    startTranslateAnimator.setInterpolator(new FastOutLinearInInterpolator());

    ValueAnimator circleAnimator = ValueAnimator.ofFloat((float) (Math.PI / 2), (float) (5 * Math.PI / 2));
    circleAnimator.addUpdateListener(animation -> {
      Float value = (Float) animation.getAnimatedValue();

      float translationX = (float) (Math.cos(value) * DimenUtils.toDp(getContext(), 100));
      float translationY = (float) (Math.sin(value) * DimenUtils.toDp(getContext(), 100));

      cardView.setTranslationX(translationX);
      cardView.setTranslationY(translationY);
    });
    circleAnimator.setDuration(1500);
    circleAnimator.setInterpolator(new LinearInterpolator());

    ObjectAnimator endTranslateAnimator = ObjectAnimator.ofFloat(cardView, View.TRANSLATION_Y, DimenUtils.toDp(getContext(), 100), 0);
    endTranslateAnimator.setInterpolator(new LinearOutSlowInInterpolator());

    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playSequentially(startTranslateAnimator, circleAnimator, endTranslateAnimator);

    animatorSet.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        positionButton.setEnabled(true);
      }

      @Override public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
        positionButton.setEnabled(false);
      }
    });

    animatorSet.start();
  }

  @OnClick(R.id.property_rotation_button) public void onRotationButtonClick() {
    ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(cardView, View.ROTATION, 0, 1080);
    rotationAnimator.setDuration(1800);
    rotationAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    rotationAnimator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        rotationButton.setEnabled(true);
      }

      @Override public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
        rotationButton.setEnabled(false);
      }
    });

    rotationAnimator.start();
  }

  @OnClick(R.id.property_size_button) public void onSizeButtonClick() {
    ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1, 0.2f, 1);

    scaleAnimator.addUpdateListener(animation -> {
      Float value = (Float) animation.getAnimatedValue();
      cardView.setScaleX(value);
      cardView.setScaleY(value);
    });

    scaleAnimator.setDuration(1000);
    scaleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    scaleAnimator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        sizeButton.setEnabled(true);
      }

      @Override public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
        sizeButton.setEnabled(false);
      }
    });

    scaleAnimator.start();
  }

  @OnClick(R.id.property_alpha_button) public void onAlphaButtonClick() {
    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(cardView, View.ALPHA, 1, 0, 1);
    alphaAnimator.setDuration(1000);
    alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    alphaAnimator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        alphaButton.setEnabled(true);
      }

      @Override public void onAnimationStart(Animator animation) {
        super.onAnimationStart(animation);
        alphaButton.setEnabled(false);
      }
    });

    alphaAnimator.start();
  }
}
