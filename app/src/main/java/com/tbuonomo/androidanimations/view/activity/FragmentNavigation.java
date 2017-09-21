package com.tbuonomo.androidanimations.view.activity;

import android.view.View;

/**
 * Created by tommy on 17/09/17.
 */

public interface FragmentNavigation {
  /**
   * Navigate to the nature item detail screen using fragment to fragment transition.
   */
  void navigateToNatureDetailFragment(int natureResId, View sharedElement);

  /**
   * Navigate to the nature item detail screen using fragment to activity transition.
   */
  void navigateToNatureDetailActivity(int natureResId, View sharedElement);
}
