package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api;

import android.support.annotation.NonNull;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.MoviesResponse;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts.MoviesProvider;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.rx.utils.CursorListMapper;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.rx.utils.CursorObservable;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class MoviesRepository {

    private final MovieService mService;

    public MoviesRepository(@NonNull MovieService service) {
        mService = service;
    }

    @NonNull
    public Observable<List<Movie>> loadMovies(@NonNull MoviesProvider.Type type) {
        Observable<List<Movie>> observable;
        if (type == MoviesProvider.Type.FAVOURITE) {
            observable = CursorObservable.create(MoviesProvider.movies(type))
                    .map(new CursorListMapper<>(MoviesProvider::fromCursor));
        } else if (type == MoviesProvider.Type.POPULAR) {
            observable = mService.popularMovies().map(MoviesResponse::getMovies);
        } else {
            observable = mService.topRatedMovies().map(MoviesResponse::getMovies);
        }
        return observable
                .onErrorResumeNext(throwable -> {
                    return CursorObservable.create(MoviesProvider.movies(type))
                            .map(new CursorListMapper<>(MoviesProvider::fromCursor));
                })
                .doOnNext(movies -> MoviesProvider.save(movies, type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
