package com.tbuonomo.androidanimations.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tbuonomo.androidanimations.R;
import com.tbuonomo.androidanimations.view.fragment.SpringExampleFragment;
import com.tbuonomo.androidanimations.view.fragment.WelcomeFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

  private void replaceFragment(SpringExampleFragment springExampleFragment) {
    fragmentManager.beginTransaction().replace(R.id.fragment_container, springExampleFragment).commit();
  }

  @SuppressWarnings("StatementWithEmptyBody") @Override public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    switch (id) {
      case R.id.nav_view_animation:
        break;
      case R.id.nav_property_animation:
        break;
      case R.id.nav_share:
        break;
      case R.id.nav_send:
        break;
      case R.id.nav_spring:
        replaceFragment(new SpringExampleFragment());
        break;
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
