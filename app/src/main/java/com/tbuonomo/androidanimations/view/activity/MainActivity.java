package com.tbuonomo.androidanimations.view.activity;

import android.content.Context;
import android.content.Intent;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.fragment.FlingListFragment;
import com.tbuonomo.androidanimations.view.fragment.InterpolatorsFragment;
import com.tbuonomo.androidanimations.view.fragment.NatureDetailFragment;
import com.tbuonomo.androidanimations.view.fragment.NatureListFragment;
import com.tbuonomo.androidanimations.view.fragment.SpringDragFragment;
import com.tbuonomo.androidanimations.view.fragment.WelcomeFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentNavigation {

  @BindView(R.id.toolbar) Toolbar toolbar;
  private FragmentManager fragmentManager;

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
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
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
    getMenuInflater().inflate(R.menu.main, menu);
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
