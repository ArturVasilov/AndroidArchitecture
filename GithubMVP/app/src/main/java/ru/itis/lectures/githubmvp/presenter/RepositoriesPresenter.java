package ru.itis.lectures.githubmvp.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.content.Repository;
import ru.itis.lectures.githubmvp.repository.RepositoriesRepository;
import ru.itis.lectures.githubmvp.rx.RxLoader;
import ru.itis.lectures.githubmvp.view.RepositoriesView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class RepositoriesPresenter {

    private final Context mContext;
    private final LoaderManager mLm;

    private final RepositoriesView mView;
    private final RepositoriesRepository mRepository;

    public RepositoriesPresenter(@NonNull Context context, @NonNull LoaderManager lm,
                                 @NonNull RepositoriesView view,
                                 @NonNull RepositoriesRepository repository) {
        mContext = context;
        mLm = lm;
        mView = view;
        mRepository = repository;
    }

    public void dispatchCreated() {
        RxLoader.create(mContext, mLm, R.id.local_repositories_loader,
                mRepository.cachedRepositories()
                        .subscribeOn(Schedulers.io())
                        .doOnTerminate(this::refresh)
                        .observeOn(AndroidSchedulers.mainThread()))
                .init(mView::showRepositories, throwable -> mView.showError());
    }

    public void refresh() {
        RxLoader.create(mContext, mLm, R.id.repositories_loader,
                ApiFactory.getProvider().provideGithubService()
                        .repositories()
                        .flatMap(repositories -> {
                            mRepository.saveRepositories(repositories);
                            return Observable.just(repositories);
                        })
                        .doOnSubscribe(mView::showLoading)
                        .doOnTerminate(mView::hideLoading)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()))
                .init(mView::showRepositories, throwable -> mView.showError());
    }

    public void onItemClick(@NonNull Repository repository) {
        mView.showCommits(repository);
    }
}
