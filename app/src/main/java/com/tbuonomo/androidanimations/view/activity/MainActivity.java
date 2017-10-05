package com.tbuonomo.androidanimations.view.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.SimpleColorFilter;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.fragment.FlingListFragment;
import com.tbuonomo.androidanimations.view.fragment.InterpolatorsFragment;
import com.tbuonomo.androidanimations.view.fragment.NatureDetailFragment;
import com.tbuonomo.androidanimations.view.fragment.NatureListFragment;
import com.tbuonomo.androidanimations.view.fragment.SpringDragFragment;
import com.tbuonomo.androidanimations.view.fragment.WelcomeFragment;
import com.tbuonomo.androidanimations.view.util.DimenUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentNavigation {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.nav_view) NavigationView navigationView;
  private FragmentManager fragmentManager;
  private ValueAnimator subMenuViewAnimator;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    initDrawer();
    initFragmentManager();
  }

  private void initFragmentManager() {
    fragmentManager = getSupportFragmentManager();

    fragmentManager.beginTransaction().add(R.id.fragment_container, new WelcomeFragment()).commit();
  }

  private void initDrawer() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    navigationView.setNavigationItemSelectedListener(this);

    navigationView.post(() -> {
      ViewGroup navigationViewGroup = (ViewGroup) navigationView.getChildAt(0);
      subMenuViewAnimator = ValueAnimator.ofFloat(DimenUtils.toDp(MainActivity.this, 300), 0);
      subMenuViewAnimator.setDuration(1000);
      subMenuViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
      subMenuViewAnimator.addUpdateListener(valueAnimator -> {
        for (int i = 1; i < navigationViewGroup.getChildCount(); i++) {
          navigationViewGroup.getChildAt(i).setTranslationX((Float) valueAnimator.getAnimatedValue());
        }
      });
    });

    LottieAnimationView waveLottieView = navigationView.getHeaderView(0).findViewById(R.id.header_lottie_wave);
    waveLottieView.addColorFilter(new SimpleColorFilter(getColor(R.color.colorAccent)));
    waveLottieView.addColorFilterToLayer("background", new SimpleColorFilter(Color.WHITE));
    waveLottieView.setMaxProgress(0.33f);

    drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
      private ValueAnimator animator;

      @Override public void onDrawerSlide(View drawerView, float slideOffset) {
        // Header Lottie Animation
        if (slideOffset > 0 && (animator == null || !animator.isRunning())) {
          // Custom animation duration.
          animator = ValueAnimator.ofFloat(0f, 0.33f).setDuration(20000);
          animator.setRepeatCount(ValueAnimator.INFINITE);
          animator.setInterpolator(new LinearInterpolator());

          animator.addUpdateListener(animation -> {
            waveLottieView.setProgress((Float) animation.getAnimatedValue());
          });
          animator.start();
        } else if (slideOffset == 0) {
          animator.cancel();
        }

        if (subMenuViewAnimator != null) {
          subMenuViewAnimator.setCurrentPlayTime((long) (slideOffset * (float) subMenuViewAnimator.getDuration()));
        }
      }
    });
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void replaceFragment(Fragment fragment) {
    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
  }

  @SuppressWarnings("StatementWithEmptyBody") @Override public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    switch (id) {
      case R.id.nav_welcome:
        replaceFragment(new WelcomeFragment());
        break;
      case R.id.nav_view_animation:
        break;
      case R.id.nav_interpolator:
        replaceFragment(new InterpolatorsFragment());
        break;
      case R.id.nav_spring:
        replaceFragment(new SpringDragFragment());
        break;
      case R.id.nav_shared_element:
        replaceFragment(new NatureListFragment());
        break;
      case R.id.nav_fling:
        replaceFragment(new FlingListFragment());
        break;
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override public void navigateToNatureDetailFragment(int natureResId, View sharedElement) {
    Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
    currentFragment.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));

    NatureDetailFragment fragment = NatureDetailFragment.newInstance(natureResId);
    fragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.fade));
    fragmentManager.beginTransaction()
        .addSharedElement(sharedElement, ViewCompat.getTransitionName(sharedElement))
        .addToBackStack(NatureDetailFragment.class.getSimpleName())
        .replace(R.id.fragment_container, fragment)
        .commit();
  }

  @Override public void navigateToNatureDetailActivity(int natureResId, View sharedElement) {
    Intent intent = new Intent(this, NatureDetailActivity.class);
    intent.putExtra(NatureDetailActivity.NATURE_RES_ID, natureResId);

    ActivityOptionsCompat options =
        ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElement, ViewCompat.getTransitionName(sharedElement));
    startActivity(intent, options.toBundle());
  }
}
