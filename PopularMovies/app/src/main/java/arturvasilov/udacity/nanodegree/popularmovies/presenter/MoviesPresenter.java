package arturvasilov.udacity.nanodegree.popularmovies.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmovies.R;
import arturvasilov.udacity.nanodegree.popularmovies.api.ApiFactory;
import arturvasilov.udacity.nanodegree.popularmovies.app.Preferences;
import arturvasilov.udacity.nanodegree.popularmovies.model.Movie;
import arturvasilov.udacity.nanodegree.popularmovies.model.MoviesResponse;
import arturvasilov.udacity.nanodegree.popularmovies.rx.RxLoader;
import arturvasilov.udacity.nanodegree.popularmovies.view.MoviesView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class MoviesPresenter {

    private final Context mContext;
    private final MoviesView mView;
    private final LoaderManager mLm;

    private boolean mIsPopular;

    public MoviesPresenter(@NonNull Context context, @NonNull MoviesView view, @NonNull LoaderManager lm) {
        mContext = context;
        mView = view;
        mLm = lm;
    }

    public void init() {
        mIsPopular = Preferences.isPopularMovies(mContext);
        load(false);
    }

    public void onResume() {
        boolean isPopular = Preferences.isPopularMovies(mContext);
        if (isPopular != mIsPopular) {
            mIsPopular = isPopular;
            load(true);
        }
    }

    private void load(boolean restart) {
        Observable<MoviesResponse> observable = mIsPopular
                ? ApiFactory.getMoviesService().popularMovies()
                : ApiFactory.getMoviesService().topRatedMovies();
        Observable<List<Movie>> movies = Observable.defer(() -> observable
                .doOnSubscribe(mView::showLoading)
                .doAfterTerminate(mView::hideLoading)
                .map(MoviesResponse::getMovies)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()));

        RxLoader<List<Movie>> loader = RxLoader.create(mContext, mLm, R.id.movies_loader_id, movies);
        if (restart) {
            loader.restart(mView::showMovies, throwable -> mView.showError());
        } else {
            loader.init(mView::showMovies, throwable -> mView.showError());
        }
    }
}
