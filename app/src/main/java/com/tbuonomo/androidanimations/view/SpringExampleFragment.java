package com.tbuonomo.androidanimations.view;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;

/**
 * Created by tommy on 04/09/17.
 */

public class SpringExampleFragment extends Fragment {
  @BindView(R.id.spring_card_view) CardView cardView;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.spring_fragment, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    SpringAnimation springAnimation = new SpringAnimation(cardView, DynamicAnimation.TRANSLATION_X, 0);

    GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
      @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(SpringExampleFragment.class.getSimpleName(), "onScroll: " + e1.toString() + " " + e2.toString() + " " + distanceX + " " + distanceY);
        return true;
      }

      @Override public boolean onDown(MotionEvent e) {
        Log.i(SpringExampleFragment.class.getSimpleName(), "onDown: " + e);
        return super.onDown(e);
      }
    };

    GestureDetectorCompat gestureDetector = new GestureDetectorCompat(getContext(), gestureListener);
    cardView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
  }
}
