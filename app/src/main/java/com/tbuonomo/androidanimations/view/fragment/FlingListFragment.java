package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
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

  private static final float MIN_FRICTION = 1.0f;
  private static final float MAX_FRICTION = 4.0f;
  @BindView(R.id.fling_recycler_view) LinearLayout linearLayout;
  @BindView(R.id.fling_scroll_view) HorizontalScrollView scrollView;
  @BindView(R.id.fling_friction_text) TextView frictionTextView;
  @BindView(R.id.fling_friction_seekbar) SeekBar frictionSeekBar;
  private float flingFriction = MIN_FRICTION;

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

    GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
      @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(FlingListFragment.class.getSimpleName(), "onFling: " + velocityX);

        FlingAnimation flingAnimation = new FlingAnimation(scrollView, DynamicAnimation.SCROLL_X);
        flingAnimation.setStartVelocity(-velocityX).setMinValue(0).setMaxValue(100000).setFriction(flingFriction).start();

        return true;
      }
    };

    GestureDetector gestureDetector = new GestureDetector(getContext(), gestureListener);

    scrollView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
      }
    });

    frictionSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        flingFriction = MIN_FRICTION + progress / 100f * (MAX_FRICTION - MIN_FRICTION);
        refreshSeekBars();
      }

      @Override public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });

    refreshSeekBars();
  }

  private void refreshSeekBars() {
    frictionTextView.setText(getString(R.string.friction, String.valueOf(flingFriction)));
  }
}
