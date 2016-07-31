package ru.arturvasilov.stackexchangeclient.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Locale;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.rx.RxDecor;
import ru.arturvasilov.stackexchangeclient.rx.rxloader.RxLoader;
import ru.arturvasilov.stackexchangeclient.view.AnswersListView;
import ru.arturvasilov.stackexchangeclient.view.ErrorView;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;

/**
 * @author Artur Vasilov
 */
public class AnswersListPresenter {

    private static final String ERROR_SHOWN_KEY = "error_shown";

    private static final String QUESTION_LINK_FORMAT = "http://stackoverflow.com/a/%1$d/%2$d";

    private final Context mContext;
    private final LoaderManager mLm;

    private final AnswersListView mView;
    private final LoadingView mLoadingView;
    private final ErrorView mErrorView;

    private final User mUser;

    private boolean mIsErrorShown;

    public AnswersListPresenter(@NonNull Context context, @NonNull LoaderManager lm, @NonNull AnswersListView view,
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

        mView.setEmptyText(R.string.no_answers);

        RxLoader.create(mContext, mLm, R.id.answers_loader_id, RepositoryProvider.provideRemoteRepository()
                .answers(mUser.getUserId())
                .compose(RxDecor.loading(mLoadingView)))
                .init(answers -> {
                    mView.hideEmptyListView();
                    mView.showAnswers(answers);
                }, throwable -> {
                    mView.showEmptyListView();
                    if (!mIsErrorShown) {
                        mIsErrorShown = true;
                        RxDecor.error(mErrorView).call(throwable);
                    }
                });
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(ERROR_SHOWN_KEY, mIsErrorShown);
    }

    public void onItemClick(@NonNull Answer answer) {
        String url = String.format(Locale.getDefault(), QUESTION_LINK_FORMAT,
                answer.getQuestionId(), answer.getAnswerId());
        mView.browseUrl(url);
    }
}
