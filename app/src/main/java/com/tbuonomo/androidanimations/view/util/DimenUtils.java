package com.tbuonomo.androidanimations.view.util;

import android.content.Context;

/**
 * Created by tommy on 06/09/17.
 */

public class DimenUtils {
  private DimenUtils() {
  }

  public static float toDp(Context context, float sizeInDp) {
    return context.getResources().getDisplayMetrics().density * sizeInDp;
  }

  public static float getScreenWidth(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }
}
