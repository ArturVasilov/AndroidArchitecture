package ru.arturvasilov.stackexchangeclient.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.api.ApiConstants;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.data.database.LocalRepository;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.rx.RxSchedulers;
import ru.arturvasilov.stackexchangeclient.rx.StubAction;
import ru.arturvasilov.stackexchangeclient.rx.rxloader.RxLoader;
import ru.arturvasilov.stackexchangeclient.utils.TextUtils;
import ru.arturvasilov.stackexchangeclient.view.MainView;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class MainPresenter {

    private final Context mContext;
    private final LoaderManager mLm;

    private final MainView mView;

    private User mCurrentUser;

    public MainPresenter(@NonNull Context context, @NonNull LoaderManager lm, @NonNull MainView view) {
        mContext = context;
        mLm = lm;
        mView = view;
    }

    public void init() {
        RepositoryProvider.provideLocalRepository().getCurrentUser()
                .subscribe(this::handleUser, new StubAction<>());

        RxLoader.create(mContext, mLm, R.id.user_info_loader_id,
                RepositoryProvider.provideRemoteRepository().getCurrentUser())
                .init(this::handleUser, new StubAction<>());

        showTabs();
    }

    public void onTagsResult() {
        showTabs();
    }

    public void onProfileSelected() {
        mView.openProfile(mCurrentUser);
    }

    public void onMyAnswersSelected() {
        mView.openAnswers(mCurrentUser);
    }

    private void showTabs() {
        LocalRepository repository = RepositoryProvider.provideLocalRepository();
        Observable.zip(repository.questions(ApiConstants.TAG_MY_QUESTIONS), repository.tags(),
                (myQuestions, tags) -> {
                    if (myQuestions != null && !myQuestions.isEmpty()) {
                        tags.add(0, ApiConstants.TAG_MY_QUESTIONS);
                    }
                    tags.add(0, ApiConstants.TAG_ALL);
                    return tags;
                })
                .observeOn(RxSchedulers.main())
                .doOnNext(tags -> {
                    if (tags.size() <= 1) {
                        mView.hideTabLayout();
                    }
                })
                .flatMap(Observable::from)
                .doOnNext(tag -> {
                    if (TextUtils.equals(ApiConstants.TAG_ALL, tag)) {
                        mView.addTab(mContext.getString(R.string.all));
                    } else if (TextUtils.equals(ApiConstants.TAG_MY_QUESTIONS, tag)) {
                        mView.addTab(mContext.getString(R.string.my));
                    } else {
                        mView.addTab(tag);
                    }
                })
                .toList()
                .subscribe(mView::showTags, new StubAction<>());
    }

    public void onReturnFromTags() {
        mView.clearTabs();
        showTabs();
    }

    private void handleUser(@NonNull User user) {
        mCurrentUser = user;
        mView.showUserImage(user.getProfileImage());
        mView.showUserName(user.getName());
    }
}
