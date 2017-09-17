package com.tbuonomo.androidanimations.view.fragment.util;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.View;
import com.tbuonomo.androidanimations.view.util.DimenUtils;
import javax.vecmath.Vector2f;

/**
 * Created by tommy on 15/09/17.
 */
public class WelcomeItemWrapper {
  private SpringAnimation springAnimationX;
  private SpringAnimation springAnimationY;
  private SpringForce springForceX;
  private SpringForce springForceY;
  private float factor;
  private float startX;
  private float startY;
  private View item;
  private Context context;

  public WelcomeItemWrapper(View item) {
    this(item, 1.0f);
  }

  public WelcomeItemWrapper(View item, float factor) {
    this.factor = factor;
    this.startX = item.getTranslationX();
    this.startY = item.getTranslationY();
    this.item = item;
    context = item.getContext();
    initSpring();
  }

  private void initSpring() {
    springForceX = new SpringForce(0).setDampingRatio(0.3f).setStiffness(100f);
    springForceY = new SpringForce(0).setDampingRatio(0.3f).setStiffness(100f);

    springAnimationX = new SpringAnimation(item, DynamicAnimation.TRANSLATION_X).setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_PIXELS)
        .setSpring(springForceX);
    springAnimationY = new SpringAnimation(item, DynamicAnimation.TRANSLATION_Y).setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_PIXELS)
        .setSpring(springForceY);
  }

  public void updateFinalPosition(float xTouch, float yTouch) {
    Vector2f vector2f = new Vector2f(xTouch - (startX + item.getWidth() / 2), yTouch - (startY + item.getHeight() / 2));
    if (vector2f.length() > DimenUtils.toDp(context, 64)) {
      vector2f.normalize();
      vector2f.scale(DimenUtils.toDp(context, 48));
    }
    vector2f.scale(factor);

    springForceX.setFinalPosition(startX + vector2f.getX());
    springForceY.setFinalPosition(startY + vector2f.getY());

    if (!springAnimationX.isRunning()) {
      springAnimationX.start();
    }

    if (!springAnimationY.isRunning()) {
      springAnimationY.start();
    }
  }

  public void resetFinalPosition() {
    springForceX.setFinalPosition(startX);
    springForceY.setFinalPosition(startY);

    if (!springAnimationX.isRunning()) {
      springAnimationX.start();
    }

    if (!springAnimationY.isRunning()) {
      springAnimationY.start();
    }
  }
}
