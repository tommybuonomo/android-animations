package com.tbuonomo.androidanimations.view.util;

import android.content.Context;
import android.content.res.Resources;
import com.tbuonomo.androidanimations.view.adapter.item.NatureItem;
import com.tbuonomo.androidanimations.view.adapter.item.SocialItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by tommy on 06/09/17.
 */

public class DrawableUtils {
  private DrawableUtils() {
  }

  public static List<SocialItem> getAllSocialItems(Context context) {
    List<SocialItem> socialItems = new ArrayList<>();
    Resources resources = context.getResources();
    for (int i = 0; i < 49; i++) {
      final int resourceId = resources.getIdentifier(String.format(Locale.getDefault(), "social_%03d", i), "drawable", context.getPackageName());
      socialItems.add(new SocialItem(resourceId));
    }
    return socialItems;
  }

  public static List<NatureItem> getAllNatureItems(Context context) {
    List<NatureItem> natureItems = new ArrayList<>();
    Resources resources = context.getResources();
    for (int i = 0; i < 21; i++) {
      final int resourceId = resources.getIdentifier(String.format(Locale.getDefault(), "nature_%03d", i), "drawable", context.getPackageName());
      natureItems.add(new NatureItem(resourceId));
    }
    return natureItems;
  }
}
