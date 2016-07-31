package ru.arturvasilov.stackexchangeclient.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.api.RemoteRepository;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Badge;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.model.content.UserTag;
import ru.arturvasilov.stackexchangeclient.rx.RxDecor;
import ru.arturvasilov.stackexchangeclient.rx.rxloader.RxLoader;
import ru.arturvasilov.stackexchangeclient.view.ErrorView;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import ru.arturvasilov.stackexchangeclient.view.ProfileView;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class ProfilePresenter {

    private static final String ERROR_SHOWN_KEY = "error_shown";

    private static final String LINK_FORMAT = "<a href=\"%1$s\">%2$s</a>";

    private final Context mContext;
    private final LoaderManager mLm;

    private final ProfileView mView;
    private final LoadingView mLoadingView;
    private final ErrorView mErrorView;

    private final User mUser;

    private boolean mIsErrorShown = false;

    public ProfilePresenter(@NonNull Context context, @NonNull LoaderManager lm, @NonNull ProfileView view,
                            @NonNull LoadingView loadingView, @NonNull ErrorView errorView, @NonNull User user) {
        mContext = context;
        mLm = lm;
        mView = view;
        mLoadingView = loadingView;
        mErrorView = errorView;
        mUser = user;
    }

    public void init(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mIsErrorShown = savedInstanceState.getBoolean(ERROR_SHOWN_KEY);
        }

        RemoteRepository repository = RepositoryProvider.provideRemoteRepository();
        int userId = mUser.getUserId();
        Observable<Pair<List<Badge>, List<UserTag>>> observable =
                Observable.zip(repository.badges(userId), repository.topTags(userId), Pair::create)
                        .compose(RxDecor.loading(mLoadingView));

        RxLoader.create(mContext, mLm, R.id.user_full_info_loader_id, observable)
                .init(pair -> {
                    List<Badge> badges = pair.first;
                    List<UserTag> userTags = pair.second;

                    showUserInfo();
                    if (badges != null && !badges.isEmpty()) {
                        mView.showBadges(badges);
                    }
                    if (userTags != null && !userTags.isEmpty()) {
                        mView.showTopTags(userTags);
                    }
                }, throwable -> {
                    showUserInfo();
                    if (!mIsErrorShown) {
                        mIsErrorShown = true;
                        RxDecor.error(mErrorView).call(throwable);
                    }
                });
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(ERROR_SHOWN_KEY, mIsErrorShown);
    }

    private void showUserInfo() {
        mView.showUserName(mUser.getName());
        String imageLink = mUser.getProfileImage().replace("s=128", "s=800").replace("sz=128", "sz=800");
        mView.showUserImage(imageLink);
        mView.showReputation(mContext.getString(R.string.reputation, mUser.getReputation()));
        mView.showProfileLink(String.format(LINK_FORMAT, mUser.getLink(), mContext.getString(R.string.profile_text)));
    }
}
