package com.tbuonomo.androidanimations.view.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.Window;
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
  @BindView(R.id.nature_floating_button) FloatingActionButton floatingActionButton;
  @BindView(R.id.nature_nested_scroll_view) NestedScrollView nestedScrollView;

  @BindView(R.id.item_nature_image) ImageView natureImage;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
    getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.fade));
    setContentView(R.layout.activity_nature_detail);
    ButterKnife.bind(this);
    setUpToolbar();
    initNatureImage();
  }

  private void setUpToolbar() {
    getWindow().setStatusBarColor(Color.TRANSPARENT);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("");
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
            createPaletteAsync(resource);
            startPostponedEnterTransition();
            return false;
          }
        })
        .into(natureImage);
  }

  public void createPaletteAsync(Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
      Palette.from(((BitmapDrawable) drawable).getBitmap()).generate(p -> {
        int floatingColor = p.getLightVibrantColor(p.getDominantColor(getColor(R.color.colorAccent)));
        int backgroundColor = p.getVibrantColor(p.getMutedColor(getColor(R.color.colorPrimaryDark)));
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(floatingColor));
        nestedScrollView.setBackgroundColor(backgroundColor);
      });
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
    if (menuItem.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(menuItem);
  }

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
