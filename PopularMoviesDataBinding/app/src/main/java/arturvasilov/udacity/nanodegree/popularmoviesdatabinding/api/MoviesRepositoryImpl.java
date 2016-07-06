package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.api;

import android.support.annotation.NonNull;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Review;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Video;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.contracts.MoviesProvider;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.response.MoviesResponse;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.response.ReviewsResponse;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.response.VideosResponse;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.rx.utils.AsyncOperator;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.rx.utils.CursorListMapper;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.rx.utils.CursorObservable;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Artur Vasilov
 */
public class MoviesRepositoryImpl implements MoviesRepository {

    private final MovieService mService;

    public MoviesRepositoryImpl(@NonNull MovieService service) {
        mService = service;
    }

    @NonNull
    @Override
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
                .zipWith(CursorObservable.create(MoviesProvider.movies(MoviesProvider.Type.FAVOURITE))
                        .map(new CursorListMapper<>(MoviesProvider::fromCursor)), this::markFavourites)
                .compose(new AsyncOperator<>());
    }

    @NonNull
    @Override
    public Observable<List<Review>> reviews(@NonNull Movie movie) {
        return mService.reviews(String.valueOf(movie.getId()))
                .map(ReviewsResponse::getReviews);
    }

    @NonNull
    @Override
    public Observable<List<Video>> videos(@NonNull Movie movie) {
        return mService.video(String.valueOf(movie.getId()))
                .map(VideosResponse::getVideos);
    }

    @NonNull
    @Override
    public Observable<Boolean> addToFavourite(@NonNull Movie movie) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                MoviesProvider.save(movie, MoviesProvider.Type.FAVOURITE);
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        })
                .compose(new AsyncOperator<>());
    }

    @NonNull
    @Override
    public Observable<Boolean> removeFromFavourite(@NonNull Movie movie) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                MoviesProvider.delete(movie);
                subscriber.onNext(false);
                subscriber.onCompleted();
            }
        })
                .compose(new AsyncOperator<>());
    }

    @NonNull
    private List<Movie> markFavourites(@NonNull List<Movie> movies, @NonNull List<Movie> favouriteMovies) {
        for (Movie movie : movies) {
            for (Movie favouriteMovie : favouriteMovies) {
                if (movie.getId() == favouriteMovie.getId()) {
                    movie.setFavourite(true);
                    break;
                }
            }
        }
        return movies;
    }
}
