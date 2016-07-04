package arturvasilov.udacity.nanodegree.popularmovies.view;

import android.support.annotation.NonNull;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmovies.model.Movie;

/**
 * @author Artur Vasilov
 */
public interface MoviesView extends LoadingView {

    void showMovies(@NonNull List<Movie> movies);

}
