package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.adapter.item.SocialItem;
import com.tbuonomo.androidanimations.view.util.DrawableUtils;

/**
 * Created by tommy on 04/09/17.
 */

public class FlingListFragment extends Fragment {
  public static final float MIN_DAMPING = 0.0f;
  public static final float MAX_DAMPING = 1.0f;
  private static final float MIN_STIFFNESS = 0.1f;
  private static final float MAX_STIFFNESS = 1500f;
  private static final float MIN_FRICTION = 1.0f;
  private static final float MAX_FRICTION = 4.0f;

  @BindView(R.id.fling_recycler_view) LinearLayout linearLayout;
  @BindView(R.id.fling_scroll_view) HorizontalScrollView scrollView;
  @BindView(R.id.fling_friction_text) TextView frictionTextView;
  @BindView(R.id.fling_friction_seekbar) SeekBar frictionSeekBar;
  @BindView(R.id.stiffness_seekbar) SeekBar stiffnessSeekBar;
  @BindView(R.id.stiffness_text) TextView stiffnessTextView;
  @BindView(R.id.damping_ratio_seekbar) SeekBar dampingRationSeekBar;
  @BindView(R.id.damping_ratio_text) TextView dampingRationTextView;

  private float flingFriction = MIN_FRICTION;
  private SpringForce springForce;
  private SpringAnimation springAnimation;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_fling_list, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    for (SocialItem socialItem : DrawableUtils.getAllSocialItems(getContext())) {
      View v = LayoutInflater.from(getContext()).inflate(R.layout.item_social_icon, linearLayout, false);
      ImageView image = v.findViewById(R.id.item_social_image);
      image.setImageDrawable(ContextCompat.getDrawable(getContext(), socialItem.getDrawableResId()));
      linearLayout.addView(v);
    }

    setUpFlingAnimation();

    setUpSpringAnimation();

    setUpFrictionSeekBar();

    refreshSeekBars();
  }

  private void setUpSpringAnimation() {
    springForce = new SpringForce(0).setDampingRatio(0.5f).setStiffness(110f);

    FloatPropertyCompat<ViewGroup> floatPropertyCompat = new FloatPropertyCompat<ViewGroup>("") {
      @Override public float getValue(ViewGroup viewGroup) {
        return viewGroup.getChildAt(0).getRotation();
      }

      @Override public void setValue(ViewGroup viewGroup, float value) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
          viewGroup.getChildAt(i).setRotation(value);
        }
      }

      @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(FlingListFragment.class.getSimpleName(), "onScroll: " + distanceX + " " + distanceY);
        return super.onScroll(e1, e2, distanceX, distanceY);
      }
    };

    springAnimation = new SpringAnimation(linearLayout, floatPropertyCompat);
    springAnimation.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_ROTATION_DEGREES);
    springAnimation.setSpring(springForce);
    springAnimation.start();
  }

  private void setUpFrictionSeekBar() {
    frictionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        flingFriction = MIN_FRICTION + progress / 100f * (MAX_FRICTION - MIN_FRICTION);
        refreshSeekBars();
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

<<<<<<< Updated upstream
      }
    });
=======
    scrollView.setOnTouchListener((view1, motionEvent) -> gestureDetector.onTouchEvent(motionEvent));
>>>>>>> Stashed changes

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

    stiffnessSeekBar.setProgress((int) (MIN_STIFFNESS + (springForce.getStiffness() / MAX_STIFFNESS) * 100));
    dampingRationSeekBar.setProgress((int) (MIN_DAMPING + (springForce.getDampingRatio() / MAX_DAMPING) * 100));
    frictionSeekBar.setProgress((int) (MIN_FRICTION + (flingFriction / MAX_FRICTION) * 100));
  }

  private void setUpFlingAnimation() {
    GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
      @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(FlingListFragment.class.getSimpleName(), "onFling: " + velocityX);

        FlingAnimation flingAnimation = new FlingAnimation(scrollView, DynamicAnimation.SCROLL_X);
        flingAnimation.setStartVelocity(-velocityX).setMinValue(0).setMaxValue(100000).setFriction(flingFriction).start();

        springForce.setFinalPosition(0);

        return true;
      }

      @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        springForce.setFinalPosition(distanceX);
        if (!springAnimation.isRunning()) {
          springAnimation.start();
        }
        return false;
      }
    };

    GestureDetector gestureDetector = new GestureDetector(getContext(), gestureListener);

    scrollView.setOnTouchListener((view1, motionEvent) -> gestureDetector.onTouchEvent(motionEvent));
  }

  private void refreshSeekBars() {
    frictionTextView.setText(getString(R.string.friction, String.valueOf(flingFriction)));
    stiffnessTextView.setText(getString(R.string.stiffness, String.valueOf(springForce.getStiffness())));
    dampingRationTextView.setText(getString(R.string.damping_ratio, String.valueOf(springForce.getDampingRatio())));
  }
}
