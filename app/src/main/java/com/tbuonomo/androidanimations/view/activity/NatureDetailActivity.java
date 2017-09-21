package com.tbuonomo.androidanimations.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.config.GlideApp;
import com.tbuonomo.androidanimations.view.util.DimenUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NatureDetailActivity extends AppCompatActivity {
  public static final String NATURE_RES_ID = "NATURE_RES_ID";

  @BindView(R.id.nature_toolbar) Toolbar toolbar;

  @BindView(R.id.item_nature_image) ImageView natureImage;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nature_detail);
    ButterKnife.bind(this);
    getWindow().setStatusBarColor(Color.TRANSPARENT);
    setSupportActionBar(toolbar);

    initNatureImage();
  }

  private void initNatureImage() {
    // Wait for launching enter transition
    postponeEnterTransition();

    int resId = getIntent().getIntExtra(NATURE_RES_ID, 0);

    natureImage.setTransitionName(String.valueOf(resId));

    GlideApp.with(this)
        .load(resId)
        .override((int) DimenUtils.getScreenWidth(this), (int) DimenUtils.toDp(this, 400))
        .listener(new RequestListener<Drawable>() {
          @Override public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            startPostponedEnterTransition();
            return false;
          }

          @Override
          public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            startPostponedEnterTransition();
            return false;
          }
        })
        .into(natureImage);
  }

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
