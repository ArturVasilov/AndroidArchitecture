package ru.arturvasilov.stackexchangeclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.text.method.LinkMovementMethod;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.app.GsonHolder;
import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventKeys;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventTags;
import ru.arturvasilov.stackexchangeclient.dialog.LoadingDialog;
import ru.arturvasilov.stackexchangeclient.model.content.Badge;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.model.content.UserTag;
import ru.arturvasilov.stackexchangeclient.presenter.ProfilePresenter;
import ru.arturvasilov.stackexchangeclient.rx.RxError;
import ru.arturvasilov.stackexchangeclient.utils.HtmlCompat;
import ru.arturvasilov.stackexchangeclient.utils.Views;
import ru.arturvasilov.stackexchangeclient.view.ProfileView;
import ru.arturvasilov.stackexchangeclient.widget.BadgeButton;
import ru.arturvasilov.stackexchangeclient.widget.UserTagView;

/**
 * @author Artur Vasilov
 */
public class ProfileActivity extends AppCompatActivity implements ProfileView {

    public static final String IMAGE = "image";

    private static final String USER_KEY = "user";

    @Nullable
    private TextView mPersonName;
    private ImageView mPersonImage;
    private TextView mReputation;
    private TextView mProfileLink;

    private View mBadgesDivider;
    private View mBadgesTitle;
    private GridLayout mBadgesGrid;

    private View mTagsDivider;
    private View mTagsTitle;
    private ViewGroup mTagsLayout;

    private ProfilePresenter mPresenter;

    public static void start(@NonNull Activity activity, @NonNull View transitionImage, @NonNull User user) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra(USER_KEY, user);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareWindow();
        setContentView(R.layout.ac_profile);
        setupActionBar();

        mPersonName = Views.findById(this, R.id.userName);
        mPersonImage = Views.findById(this, R.id.image);
        mReputation = Views.findById(this, R.id.reputationText);
        mProfileLink = Views.findById(this, R.id.linkText);
        mProfileLink.setMovementMethod(LinkMovementMethod.getInstance());
        ViewCompat.setTransitionName(mPersonImage, IMAGE);

        mBadgesDivider = Views.findById(this, R.id.badgesDivider);
        mBadgesTitle = Views.findById(this, R.id.badgesTitle);
        mBadgesGrid = Views.findById(this, R.id.badgesGrid);

        mTagsDivider = Views.findById(this, R.id.tagsDivider);
        mTagsTitle = Views.findById(this, R.id.tagsTitle);
        mTagsLayout = Views.findById(this, R.id.tagsLayout);

        User user = (User) getIntent().getSerializableExtra(USER_KEY);
        Analytics.buildEvent()
                .putString(EventKeys.PROFILE_USER, GsonHolder.getGson().toJson(user))
                .log(EventTags.SCREEN_PROFILE);

        mPresenter = new ProfilePresenter(this, getLoaderManager(), this,
                LoadingDialog.view(getSupportFragmentManager()),
                RxError.view(this, getSupportFragmentManager()),
                user);
        mPresenter.init(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void showUserName(@NonNull String name) {
        if (mPersonName != null) {
            mPersonName.setText(name);
        } else if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(name);
        }
    }

    @Override
    public void showUserImage(@NonNull String url) {
        Picasso.with(this)
                .load(url)
                .into(mPersonImage);
    }

    @Override
    public void showProfileLink(@NonNull String profileLink) {
        mProfileLink.setText(HtmlCompat.fromHtml(profileLink));
    }

    @Override
    public void showReputation(@NonNull String reputation) {
        mReputation.setText(reputation);
    }

    @Override
    public void showBadges(@NonNull List<Badge> badges) {
        mBadgesGrid.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Badge badge : badges) {
            BadgeButton view = (BadgeButton) inflater.inflate(R.layout.badge_item, mBadgesGrid, false);
            view.setBadge(badge);
            mBadgesGrid.addView(view);
        }

        mBadgesDivider.setVisibility(View.VISIBLE);
        mBadgesTitle.setVisibility(View.VISIBLE);
        mBadgesGrid.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTopTags(@NonNull List<UserTag> tags) {
        mTagsLayout.removeAllViews();
        for (UserTag userTag : tags) {
            mTagsLayout.addView(new UserTagView(this, userTag));
        }

        mTagsDivider.setVisibility(View.VISIBLE);
        mTagsTitle.setVisibility(View.VISIBLE);
        mTagsLayout.setVisibility(View.VISIBLE);
    }

    private void prepareWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            Transition enter = TransitionInflater.from(this).inflateTransition(R.transition.profile_transition_enter);
            enter.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(enter);

            Transition exit = TransitionInflater.from(this).inflateTransition(R.transition.profile_transition_exit);
            exit.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setReturnTransition(exit);
        }
    }

    private void setupActionBar() {
        setSupportActionBar(Views.findById(this, R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}
