package com.tbuonomo.androidanimations.view.adapter.item;

import android.support.annotation.DrawableRes;

/**
 * Created by tommy on 06/09/17.
 */

public class SocialItem {
  @DrawableRes int drawableResId;

  public SocialItem(int drawableResId) {
    this.drawableResId = drawableResId;
  }

  public int getDrawableResId() {
    return drawableResId;
  }
}
