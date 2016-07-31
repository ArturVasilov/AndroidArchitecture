package ru.arturvasilov.stackexchangeclient.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.adapter.MainScreenAdapter;
import ru.arturvasilov.stackexchangeclient.app.Env;
import ru.arturvasilov.stackexchangeclient.app.GsonHolder;
import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventKeys;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventTags;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.presenter.MainPresenter;
import ru.arturvasilov.stackexchangeclient.utils.PicassoUtils;
import ru.arturvasilov.stackexchangeclient.utils.Views;
import ru.arturvasilov.stackexchangeclient.view.MainView;
import ru.arturvasilov.stackexchangeclient.widget.CustomViewPager;

/**
 * @author Artur Vasilov
 */
public class MainActivity extends AppCompatActivity implements MainView,
        NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    private static final int TAGS_ACTIVITY_REQUEST_CODE = 101;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView mHeaderImage;
    private TextView mHeaderText;

    private TabLayout mTabLayout;
    private CustomViewPager mPager;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        Analytics.buildEvent().log(EventTags.SCREEN_MAIN);
        Toolbar toolbar = Views.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mDrawerLayout = Views.findById(this, R.id.drawerLayout);
        mNavigationView = Views.findById(this, R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
        mHeaderImage = Views.findById(headerView, R.id.headerImage);
        mHeaderImage.setOnClickListener(view -> {
            Analytics.buildEvent()
                    .putString(EventKeys.MAIN_DRAWER_ITEM, "Image")
                    .log(EventTags.MAIN_DRAWER_ITEM_SELECTED);
            mPresenter.onProfileSelected();
        });
        mHeaderText = Views.findById(headerView, R.id.headerText);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, 0, 0);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }

        mTabLayout = Views.findById(this, R.id.tabs);
        mPager = Views.findById(this, R.id.pager);
        mTabLayout.addOnTabSelectedListener(this);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mPresenter = new MainPresenter(this, getLoaderManager(), this);
        mPresenter.init();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawer(mNavigationView);
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAGS_ACTIVITY_REQUEST_CODE) {
            mPresenter.onReturnFromTags();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String eventItem = "";
        if (item.getItemId() == R.id.profile) {
            eventItem = "Profile";
            mPresenter.onProfileSelected();
        } else if (item.getItemId() == R.id.my_answers) {
            eventItem = "My answers";
            mPresenter.onMyAnswersSelected();
        } else if (item.getItemId() == R.id.tags) {
            eventItem = "Tags";
            startActivityForResult(new Intent(this, TagsActivity.class), TAGS_ACTIVITY_REQUEST_CODE);
        } else if (item.getItemId() == R.id.exit) {
            eventItem = "Exit";
            Env.logout();
        }
        Analytics.buildEvent()
                .putString(EventKeys.MAIN_DRAWER_ITEM, eventItem)
                .log(EventTags.MAIN_DRAWER_ITEM_SELECTED);
        mDrawerLayout.closeDrawer(mNavigationView);
        return false;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // Do nothing
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        // Do nothing
    }

    @Override
    public void showUserImage(@NonNull String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .error(R.mipmap.ic_icon)
                .transform(PicassoUtils.circleTransform())
                .into(mHeaderImage);
    }

    @Override
    public void showUserName(@NonNull String name) {
        mHeaderText.setText(name);
    }

    @Override
    public void addTab(@NonNull String tabTitle) {
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle));
    }

    @Override
    public void clearTabs() {
        mTabLayout.removeAllTabs();
    }

    @Override
    public void showTags(@NonNull List<String> tags) {
        Analytics.buildEvent()
                .putString(EventKeys.MAIN_TAGS, GsonHolder.getGson().toJson(tags))
                .log(EventTags.MAIN_TABS);
        PagerAdapter adapter = new MainScreenAdapter(getSupportFragmentManager(), tags);
        mPager.setAdapter(adapter);
    }

    @Override
    public void openProfile(@NonNull User currentUser) {
        ProfileActivity.start(this, mHeaderImage, currentUser);
    }

    @Override
    public void openAnswers(@NonNull User currentUser) {
        AnswersListActivity.start(this, currentUser);
    }

    @Override
    public void hideTabLayout() {
        mTabLayout.setVisibility(View.GONE);
    }
}
