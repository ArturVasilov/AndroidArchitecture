package ru.arturvasilov.stackexchangeclient.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventTags;
import ru.arturvasilov.stackexchangeclient.presenter.AuthPresenter;
import ru.arturvasilov.stackexchangeclient.utils.TextUtils;
import ru.arturvasilov.stackexchangeclient.utils.Views;
import ru.arturvasilov.stackexchangeclient.view.AuthView;

/**
 * @author Artur Vasilov
 */
public class AuthActivity extends AppCompatActivity implements AuthView {

    private AuthPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_auth);

        Analytics.buildEvent().log(EventTags.SCREEN_AUTH);

        Views.findById(this, R.id.signInButton).setOnClickListener(view -> {
            Analytics.buildEvent().log(EventTags.AUTH_BUTTON_CLICKED);
            mPresenter.onLoginButtonClick();
        });

        mPresenter = new AuthPresenter(this);
        if (TextUtils.equals(Intent.ACTION_VIEW, getIntent().getAction())) {
            Analytics.buildEvent().log(EventTags.SUCCESS_AUTH);
            mPresenter.onSuccessUrl(getIntent().getData().toString());
            return;
        }

        mPresenter.init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (TextUtils.equals(Intent.ACTION_VIEW, intent.getAction())) {
            Analytics.buildEvent().log(EventTags.SUCCESS_AUTH);
            Uri data = intent.getData();
            mPresenter.onSuccessUrl(data.toString());
        }
    }

    @Override
    public void showAuth(@NonNull String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void showWalkthrough() {
        startActivity(new Intent(this, WalkthroughActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

    @Override
    public void showMainScreen() {
        startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

    @Override
    public void close() {
        onBackPressed();
    }
}
