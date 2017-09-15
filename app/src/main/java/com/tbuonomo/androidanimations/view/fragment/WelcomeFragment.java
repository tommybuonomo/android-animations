package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;

/**
 * Created by tommy on 03/09/17.
 */

public class WelcomeFragment extends Fragment {

  @BindView(R.id.welcome_frame_layout) FrameLayout welcomeFrameLayout;

  private boolean layoutInitialized;

  private SpringForce springForceX;
  private SpringForce springForceY;
  private SpringAnimation springAnimationX;
  private SpringAnimation springAnimationY;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    setUpSpringAnimation();
    setUpFrameLayoutAnimation();
  }

  private void setUpSpringAnimation() {
    springForceX = new SpringForce(0).setDampingRatio(0.5f).setStiffness(110f);

    FloatPropertyCompat<ViewGroup> floatPropertyCompatX = new FloatPropertyCompat<ViewGroup>("") {
      @Override public float getValue(ViewGroup viewGroup) {
        if (viewGroup.getChildCount() == 0) {
          return 0;
        }
        return viewGroup.getChildAt(0).getTranslationX();
      }

      @Override public void setValue(ViewGroup viewGroup, float value) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
          viewGroup.getChildAt(i).setTranslationX(value);
        }
      }
    };

    springAnimationX = new SpringAnimation(welcomeFrameLayout, floatPropertyCompatX);
    springAnimationX.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_PIXELS);
    springAnimationX.setSpring(springForceX);
    springAnimationX.start();

    springForceY = new SpringForce(0).setDampingRatio(0.5f).setStiffness(110f);

    FloatPropertyCompat<ViewGroup> floatPropertyCompatY = new FloatPropertyCompat<ViewGroup>("") {
      @Override public float getValue(ViewGroup viewGroup) {
        if (viewGroup.getChildCount() == 0) {
          return 0;
        }
        return viewGroup.getChildAt(0).getTranslationY();
      }

      @Override public void setValue(ViewGroup viewGroup, float value) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
          viewGroup.getChildAt(i).setTranslationY(value);
        }
      }
    };

    springAnimationY = new SpringAnimation(welcomeFrameLayout, floatPropertyCompatY);
    springAnimationY.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_PIXELS);
    springAnimationY.setSpring(springForceY);
    springAnimationY.start();
  }

  private void setUpFrameLayoutAnimation() {
    welcomeFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
      if (layoutInitialized) {
        return;
      }

      int verticalCount = 6;
      int horizontalCount = 10;
      int cardWidth = welcomeFrameLayout.getWidth() / verticalCount;
      int cardHeight = welcomeFrameLayout.getHeight() / horizontalCount;

      for (int i = 0; i < verticalCount; i++) {
        for (int j = 0; j < horizontalCount; j++) {
          View item = LayoutInflater.from(getContext()).inflate(R.layout.item_welcome_card, welcomeFrameLayout, false);
          FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) item.getLayoutParams();
          layoutParams.width = cardWidth;
          layoutParams.height = cardHeight;
          layoutParams.topMargin = j * cardHeight;
          layoutParams.leftMargin = i * cardWidth;
          CardView cardView = item.findViewById(R.id.item_welcome_card_view);
          cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
          welcomeFrameLayout.addView(item);
        }
      }
      layoutInitialized = true;
    });

    welcomeFrameLayout.setOnTouchListener(new WelcomeFrameLayoutTouchListener());
  }

  private void updateSpringAnimation(float diffX, float diffY) {
    springForceX.setFinalPosition(diffX);
    springForceY.setFinalPosition(diffY);

    if (!springAnimationX.isRunning()) {
      springAnimationX.start();
    }

    if (!springAnimationY.isRunning()) {
      springAnimationY.start();
    }
  }

  private class WelcomeFrameLayoutTouchListener implements View.OnTouchListener {
    private float startX;
    private float startY;

    @Override public boolean onTouch(View view, MotionEvent motionEvent) {
      float x = motionEvent.getRawX();
      float y = motionEvent.getRawY();
      switch (motionEvent.getAction()) {
        case MotionEvent.ACTION_DOWN:
          startX = x;
          startY = y;
          return true;

        case MotionEvent.ACTION_MOVE:
          float diffX = x - startX;
          float diffY = y - startY;
          updateSpringAnimation(diffX, diffY);
          return true;

        case MotionEvent.ACTION_UP:
          updateSpringAnimation(0, 0);
          break;
      }
      return false;
    }
  }
}
