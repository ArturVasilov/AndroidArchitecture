package ru.arturvasilov.stackexchangeclient.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.rx.StubAction;
import ru.arturvasilov.stackexchangeclient.rx.rxloader.RxLoader;
import ru.arturvasilov.stackexchangeclient.view.QuestionsListView;

/**
 * @author Artur Vasilov
 */
public class QuestionsListPresenter {

    private static final int VISIBILITY_MARGIN = 10;

    private final Context mContext;
    private final LoaderManager mLm;

    private final QuestionsListView mView;

    private final String mTag;

    private final List<Question> mQuestions;

    public QuestionsListPresenter(@NonNull Context context, @NonNull LoaderManager lm,
                                  @NonNull QuestionsListView view, @NonNull String tag) {
        mContext = context;
        mLm = lm;
        mView = view;
        mTag = tag;

        mQuestions = new ArrayList<>();
    }

    public void init() {
        RepositoryProvider.provideLocalRepository()
                .questions(mTag)
                .subscribe(this::handleQuestions, this::handleError);

        RxLoader.create(mContext, mLm, mTag.hashCode(),
                RepositoryProvider.provideRemoteRepository().questions(mTag))
                .init(this::handleQuestions, this::handleError);
    }

    public void refresh() {
        RxLoader.create(mContext, mLm, mTag.hashCode(),
                RepositoryProvider.provideRemoteRepository().questions(mTag))
                .restart(questions -> {
                    handleQuestions(questions);
                    mView.hideRefresh();
                }, this::handleError);
    }

    public void onScrolled(int lastVisibleItemPosition) {
        if (mQuestions.size() > VISIBILITY_MARGIN && mQuestions.size() - lastVisibleItemPosition <= VISIBILITY_MARGIN) {
            long toDate = mQuestions.get(mQuestions.size() - 1).getCreationDate() - 1;
            RxLoader.create(mContext, mLm, mTag.hashCode(),
                    RepositoryProvider.provideRemoteRepository().moreQuestions(mTag, toDate))
                    .restart(questions -> {
                        mQuestions.addAll(questions);
                        mView.addQuestions(questions);
                    }, new StubAction<>());
        }
    }

    private void handleQuestions(@NonNull List<Question> questions) {
        mQuestions.clear();
        mQuestions.addAll(questions);
        if (!mQuestions.isEmpty()) {
            mView.showQuestions(questions);
            mView.hideEmptyListView();
        } else {
            mView.showEmptyListView();
        }
    }

    private void handleError(@NonNull Throwable throwable) {
        if (mQuestions.isEmpty()) {
            mView.showEmptyListView();
        }
        mView.hideRefresh();
    }

}
