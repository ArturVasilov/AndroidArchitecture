package ru.arturvasilov.stackexchangeclient.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.api.ApiConstants;
import ru.arturvasilov.stackexchangeclient.api.RemoteRepository;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.rx.RxSchedulers;
import ru.arturvasilov.stackexchangeclient.rx.rxloader.RxLoader;
import ru.arturvasilov.stackexchangeclient.view.WalkthroughView;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author Artur Vasilov
 */
public class WalkthroughPresenter {

    public static final int PAGE_COUNT = 3;

    private static final String CURRENT_ITEM_KEY = "current_item";
    private static final String WALKTHROUGH_PASSED_KEY = "walkthrough_passed";
    private static final String INFORMATION_LOADED_KEY = "information_loaded";
    private static final String ERROR_KEY = "error";

    private final Context mContext;
    private final LoaderManager mLoaderManager;

    private final WalkthroughView mView;

    private int mCurrentItem = 0;

    private boolean mIsWalkthroughPassed = false;
    private boolean mIsInformationLoaded = false;
    private boolean mIsError;

    public WalkthroughPresenter(@NonNull Context context, @NonNull LoaderManager loaderManager,
                                @NonNull WalkthroughView view) {
        mContext = context;
        mLoaderManager = loaderManager;
        mView = view;
    }

    public void init(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mCurrentItem = 0;
            showCurrentBenefit();
        } else {
            mCurrentItem = savedInstanceState.getInt(CURRENT_ITEM_KEY);
            mIsWalkthroughPassed = savedInstanceState.getBoolean(WALKTHROUGH_PASSED_KEY);
            mIsInformationLoaded = savedInstanceState.getBoolean(INFORMATION_LOADED_KEY);
            mIsError = savedInstanceState.getBoolean(ERROR_KEY);

            if (mIsWalkthroughPassed) {
                checkForSuccess();
            } else {
                showCurrentBenefit();
            }
        }

        startLoading(true);
    }

    public void onActionButtonClick() {
        if (isLastBenefit()) {
            mIsWalkthroughPassed = true;
            checkForSuccess();
        } else {
            mCurrentItem++;
            showCurrentBenefit();
        }
    }

    public void onRetryButtonClick() {
        startLoading(false);
    }

    public void onBenefitSelected(boolean fromUser, int index) {
        if (fromUser) {
            mCurrentItem = index;
            showCurrentBenefit();
        }
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_ITEM_KEY, mCurrentItem);
        outState.putBoolean(WALKTHROUGH_PASSED_KEY, mIsWalkthroughPassed);
        outState.putBoolean(INFORMATION_LOADED_KEY, mIsInformationLoaded);
        outState.putBoolean(ERROR_KEY, mIsError);
    }

    private void showCurrentBenefit() {
        mView.showBenefit(mCurrentItem);
        if (isLastBenefit()) {
            mView.setActionButtonText(R.string.finish_uppercase);
        } else {
            mView.setActionButtonText(R.string.next_uppercase);
        }
    }

    private boolean isLastBenefit() {
        return mCurrentItem == PAGE_COUNT - 1;
    }

    private void startLoading(boolean init) {
        mIsInformationLoaded = false;
        mIsError = false;

        RemoteRepository repository = RepositoryProvider.provideRemoteRepository();
        Observable<Object> observable = Observable.zip(repository.getCurrentUser(), repository.questions(ApiConstants.TAG_ALL),
                repository.questions(ApiConstants.TAG_MY_QUESTIONS), this::isSuccessLoad)
                .flatMap(success -> success ? Observable.just(true) : Observable.error(new IOException()))
                .compose(RxSchedulers.async());
        RxLoader<Object> loader = RxLoader.create(mContext, mLoaderManager, R.id.walkthrough_loader_id, observable);

        Action1<Object> onNext = obj -> {
            mIsInformationLoaded = true;
            checkForSuccess();
        };
        if (init) {
            loader.init(onNext, this::handleError);
        } else {
            mView.showLoadingSplash();
            loader.restart(onNext, this::handleError);
        }
    }

    private void handleError(@NonNull Throwable throwable) {
        mIsInformationLoaded = false;
        mIsError = true;
        checkForSuccess();
    }

    private void checkForSuccess() {
        if (mIsWalkthroughPassed) {
            if (mIsInformationLoaded) {
                RepositoryProvider.provideKeyValueStorage().saveWalkthroughPassed();
                mView.finishWalkthrough();
            } else if (mIsError) {
                mView.showError();
            } else {
                mView.showLoadingSplash();
            }
        }
    }

    private boolean isSuccessLoad(@Nullable User currentUser, @Nullable List<Question> allQuestions,
                                  @Nullable List<Question> myQuestions) {
        return currentUser != null
                && allQuestions != null && !allQuestions.isEmpty()
                && myQuestions != null;
    }
}
