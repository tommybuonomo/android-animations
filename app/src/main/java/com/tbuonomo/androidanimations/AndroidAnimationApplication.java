package com.tbuonomo.androidanimations;

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by tommy on 03/09/17.
 */

public class AndroidAnimationApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();

    CalligraphyConfig.initDefault(
        new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Regular.ttf").setFontAttrId(R.attr.fontPath).build());
  }
}
