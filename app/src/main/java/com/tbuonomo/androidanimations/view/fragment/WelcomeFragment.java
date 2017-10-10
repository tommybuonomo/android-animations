package com.tbuonomo.androidanimations.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.annimon.stream.Stream;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.fragment.util.WelcomeItemWrapper;
import com.tbuonomo.androidanimations.view.util.DimenUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tommy on 03/09/17.
 */

public class WelcomeFragment extends Fragment {

  @BindView(R.id.welcome_frame_layout) FrameLayout welcomeFrameLayout;
  @BindView(R.id.welcome_center_card_view) CardView welcomeCardView;

  private List<WelcomeItemWrapper> welcomeItems;
  private boolean layoutInitialized;
  private boolean firstAnimationFinished;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    welcomeFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
      if (layoutInitialized) {
        return;
      }
      setUpFrameLayoutAnimation();
      setUpCenterCardViewAnimation();
      layoutInitialized = true;
    });
  }

  private void setUpCenterCardViewAnimation() {
    welcomeCardView.setTranslationX(welcomeFrameLayout.getWidth() / 2 - welcomeCardView.getWidth() / 2);

    ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(welcomeCardView, View.TRANSLATION_Y, -welcomeCardView.getHeight(),
        welcomeFrameLayout.getHeight() / 2 - welcomeCardView.getHeight() / 2);
    translateAnimator.setInterpolator(new OvershootInterpolator());
    translateAnimator.setDuration(2000);

    translateAnimator.addUpdateListener(valueAnimator -> {
      for (WelcomeItemWrapper welcomeItemWrapper : welcomeItems) {
        welcomeItemWrapper.updateFinalPosition(welcomeCardView.getTranslationX() + welcomeCardView.getWidth() / 2,
            welcomeCardView.getTranslationY() + welcomeCardView.getHeight() / 2);
      }
    });

    translateAnimator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        firstAnimationFinished = true;
        WelcomeItemWrapper welcomeItem = new WelcomeItemWrapper(welcomeCardView, 0.8f);
        welcomeItems.add(welcomeItem);
        Stream.of(welcomeItems).forEach(WelcomeItemWrapper::resetFinalPosition);
      }
    });

    translateAnimator.start();
  }

  private void setUpFrameLayoutAnimation() {
    int[] colors = getResources().getIntArray(R.array.welcome_colors);
    Random random = new Random();

    welcomeItems = new ArrayList<>();

    float cardSize = DimenUtils.toDp(getContext(), 34);
    float margin = DimenUtils.toDp(getContext(), 4);

    // Get the horizontal and vertical card number
    int horizontalCount = (int) (welcomeFrameLayout.getWidth() / (cardSize + margin * 2));
    int verticalCount = (int) (welcomeFrameLayout.getHeight() / (cardSize + margin * 2));

    // Calculate padding to center items
    float paddingX = (welcomeFrameLayout.getWidth() - horizontalCount * (cardSize + margin * 2)) / 2 + margin;
    float paddingY = (welcomeFrameLayout.getHeight() - verticalCount * (cardSize + margin * 2)) / 2 + margin;

    for (int i = 0; i < horizontalCount; i++) {
      for (int j = 0; j < verticalCount; j++) {

        View item = LayoutInflater.from(getContext()).inflate(R.layout.item_welcome_card, welcomeFrameLayout, false);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) item.getLayoutParams();
        layoutParams.width = (int) cardSize;
        layoutParams.height = (int) cardSize;

        float translationY = j * cardSize + paddingY + margin * 2 * j;
        float translationX = i * cardSize + paddingX + margin * 2 * i;
        item.setTranslationY(translationY);
        item.setTranslationX(translationX);

        CardView cardView = item.findViewById(R.id.item_welcome_card_view);
        cardView.setCardBackgroundColor(colors[random.nextInt(colors.length)]);
        cardView.setRadius(cardSize / 2);

        WelcomeItemWrapper welcomeItem = new WelcomeItemWrapper(item);
        welcomeItems.add(welcomeItem);

        welcomeFrameLayout.addView(item);
      }
    }

    welcomeFrameLayout.setOnTouchListener(new WelcomeFrameLayoutTouchListener());
  }

  private class WelcomeFrameLayoutTouchListener implements View.OnTouchListener {
    @Override public boolean onTouch(View view, MotionEvent motionEvent) {
      if (!firstAnimationFinished) {
        return false;
      }

      float x = motionEvent.getX();
      float y = motionEvent.getY();

      switch (motionEvent.getAction()) {
        case MotionEvent.ACTION_DOWN:
          for (WelcomeItemWrapper welcomeItemWrapper : welcomeItems) {
            welcomeItemWrapper.updateFinalPosition(x, y);
          }
          return true;

        case MotionEvent.ACTION_MOVE:
          Stream.of(welcomeItems).forEach(welcomeItemWrapper -> welcomeItemWrapper.updateFinalPosition(x, y));
          return true;

        case MotionEvent.ACTION_UP:
          Stream.of(welcomeItems).forEach(WelcomeItemWrapper::resetFinalPosition);
          break;
      }
      return false;
    }
  }
}
