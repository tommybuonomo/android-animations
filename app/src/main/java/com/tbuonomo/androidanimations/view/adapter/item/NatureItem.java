package com.tbuonomo.androidanimations.view.adapter.item;

import android.support.annotation.DrawableRes;

/**
 * Created by tommy on 06/09/17.
 */

public class NatureItem {
  @DrawableRes private int drawableResId;

  public NatureItem(int drawableResId) {
    this.drawableResId = drawableResId;
  }

  public int getDrawableResId() {
    return drawableResId;
  }
}
