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
import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.rx.RxDecor;
import ru.arturvasilov.stackexchangeclient.rx.rxloader.RxLoader;
import ru.arturvasilov.stackexchangeclient.view.ErrorView;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import ru.arturvasilov.stackexchangeclient.view.QuestionView;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class QuestionPresenter {

    private static final String ERROR_SHOWN_KEY = "error_shown";

    private final Context mContext;
    private final LoaderManager mLm;

    private final QuestionView mView;
    private final LoadingView mLoadingView;
    private final ErrorView mErrorView;

    private Question mQuestion;

    private boolean mIsErrorShown;

    public QuestionPresenter(@NonNull Context context, @NonNull LoaderManager lm, @NonNull QuestionView view,
                             @NonNull LoadingView loadingView, @NonNull ErrorView errorView, @NonNull Question question) {
        mContext = context;
        mLm = lm;
        mView = view;
        mLoadingView = loadingView;
        mErrorView = errorView;
        mQuestion = question;
    }

    public void init(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mIsErrorShown = savedInstanceState.getBoolean(ERROR_SHOWN_KEY);
        }

        RemoteRepository repository = RepositoryProvider.provideRemoteRepository();

        int questionId = mQuestion.getQuestionId();
        Observable<Pair<Question, List<Answer>>> observable = Observable.zip(
                repository.questionWithBody(questionId), repository.questionAnswers(questionId), Pair::create)
                .compose(RxDecor.loading(mLoadingView));

        RxLoader.create(mContext, mLm, R.id.question_details_loader_id, observable)
                .init(pair -> {
                    mQuestion = pair.first;
                    mView.showQuestion(mQuestion);
                    if (!pair.second.isEmpty()) {
                        mView.showAnswers(pair.second);
                    }
                }, throwable -> {
                    mView.showQuestion(mQuestion);
                    if (!mIsErrorShown) {
                        mIsErrorShown = true;
                        RxDecor.error(mErrorView).call(throwable);
                    }
                });
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(ERROR_SHOWN_KEY, mIsErrorShown);
    }

}
