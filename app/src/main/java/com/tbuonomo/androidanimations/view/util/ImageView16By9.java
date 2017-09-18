package com.tbuonomo.androidanimations.view.util;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by ebiz on 18/09/17.
 */

public class ImageView16By9 extends android.support.v7.widget.AppCompatImageView {
  public ImageView16By9(Context context) {
    super(context);
  }

  public ImageView16By9(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ImageView16By9(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, widthMeasureSpec * 16 / 9);
  }
}
