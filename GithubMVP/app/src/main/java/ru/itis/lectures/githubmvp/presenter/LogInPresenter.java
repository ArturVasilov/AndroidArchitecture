package ru.itis.lectures.githubmvp.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.content.Settings;
import ru.itis.lectures.githubmvp.content.auth.AuthorizationUtils;
import ru.itis.lectures.githubmvp.rx.RxLoader;
import ru.itis.lectures.githubmvp.utils.TextUtils;
import ru.itis.lectures.githubmvp.view.LogInView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class LogInPresenter {

    private final Context mContext;
    private final LoaderManager mLm;

    private final LogInView mView;

    public LogInPresenter(Context context, @NonNull LoaderManager lm, @NonNull LogInView view) {
        mContext = context;
        mLm = lm;
        mView = view;
    }

    public void checkIfAuthorized() {
        String token = Settings.getToken(mContext);
        if (!TextUtils.isEmpty(token)) {
            mView.openRepositoriesScreen();
        }
    }

    public void tryLogIn(@NonNull String login, @NonNull String password) {
        if (TextUtils.isEmpty(login)) {
            mView.showLoginError();
        } else if (TextUtils.isEmpty(password)) {
            mView.showPasswordError();
        } else {
            String authorizationString = AuthorizationUtils.createAuthorizationString(login, password);
            RxLoader.create(mContext, mLm, R.id.log_in_loader,
                    ApiFactory.getProvider().provideGithubService()
                            .authorize(authorizationString, AuthorizationUtils.createAuthorizationParam())
                            .flatMap(authorization -> {
                                Settings.putUserName(mContext, login);
                                Settings.putToken(mContext, authorization.getToken());
                                ApiFactory.resetProvider();
                                return Observable.just(authorization);
                            })
                            .doOnSubscribe(mView::showLoading)
                            .doOnTerminate(mView::hideLoading)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()))
                    .restart(authorization -> mView.openRepositoriesScreen(),
                            throwable -> mView.showError());
        }
    }
}
