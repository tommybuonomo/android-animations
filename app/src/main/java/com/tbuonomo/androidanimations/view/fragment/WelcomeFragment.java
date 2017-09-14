package com.tbuonomo.androidanimations.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
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

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    setUpFrameLayoutAnimation();
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
  }
}
