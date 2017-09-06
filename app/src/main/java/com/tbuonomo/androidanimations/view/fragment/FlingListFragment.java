package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.FloatValueHolder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.adapter.SocialIconsAdapter;
import com.tbuonomo.androidanimations.view.util.DrawableUtils;

/**
 * Created by tommy on 04/09/17.
 */

public class FlingListFragment extends Fragment {

  private static final float MIN_FRICTION = 1.0f;
  private static final float MAX_FRICTION = 4.0f;
  @BindView(R.id.fling_recycler_view) RecyclerView recyclerView;
  @BindView(R.id.fling_friction_text) TextView frictionTextView;
  @BindView(R.id.fling_friction_seekbar) SeekBar frictionSeekBar;
  private float flingFriction = MIN_FRICTION;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_fling_list, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setLayoutManager(layoutManager);

    SocialIconsAdapter adapter = new SocialIconsAdapter(DrawableUtils.getAllSocialItems(getContext()));
    recyclerView.setAdapter(adapter);

    //GestureDetector.OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
    //  @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    //
    //  }
    //};

    //GestureDetector gestureDetector = new GestureDetector(getContext(), gestureListener);

    //recyclerView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

    recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
      @Override public boolean onFling(int velocityX, int velocityY) {
        FloatValueHolder floatValueHolder = new FloatValueHolder();
        FlingAnimation flingAnimation = new FlingAnimation(floatValueHolder);
        flingAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
          @Override public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
            Log.i(FlingListFragment.class.getSimpleName(), "onAnimationUpdate: " + value);
            //layoutManager.scrollHorizontallyBy();
          }
        });
        flingAnimation.setStartVelocity(-velocityX).setMinValue(0).setMaxValue(100000).setFriction(flingFriction).start();
        Log.i(FlingListFragment.class.getSimpleName(), "onFling: " + recyclerView.getScrollX());
        return true;
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
