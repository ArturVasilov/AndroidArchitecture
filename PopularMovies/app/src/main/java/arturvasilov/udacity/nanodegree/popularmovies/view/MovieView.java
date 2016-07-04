package arturvasilov.udacity.nanodegree.popularmovies.view;

import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmovies.model.Movie;

/**
 * @author Artur Vasilov
 */
public interface MovieView {

    void prepareWindowForAnimation();

    void showToolbarTitle(@NonNull String title);

    void showImage(@NonNull Movie movie, @NonNull String width);

    void showMovieTitle(@NonNull String title, @NonNull String year);

    void showMovieOverview(@NonNull String overview);

    void showAverageRating(@NonNull String average, @NonNull String max);

    void closeScreen();

}
