package arturvasilov.udacity.nanodegree.popularmovies.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmovies.R;
import arturvasilov.udacity.nanodegree.popularmovies.model.Movie;
import arturvasilov.udacity.nanodegree.popularmovies.utils.Images;
import arturvasilov.udacity.nanodegree.popularmovies.view.MovieView;

/**
 * @author Artur Vasilov
 */
public class MovieDetailsPresenter {

    private static final String MAXIMUM_RATING = "10";

    private final Context mContext;
    private final MovieView mView;

    public MovieDetailsPresenter(@NonNull Context context, @NonNull MovieView view) {
        mContext = context;
        mView = view;
    }

    public void onPrepareActivity() {
        mView.prepareWindowForAnimation();
    }

    public void init(@NonNull Movie movie) {
        mView.showToolbarTitle(mContext.getString(R.string.movie_details));
        mView.showImage(movie, Images.WIDTH_780);
        String year = movie.getReleasedDate().substring(0, 4);
        mView.showMovieTitle(movie.getTitle(), year);
        mView.showMovieOverview(movie.getOverview());

        String average = String.valueOf(movie.getVoteAverage());
        average = average.length() > 3 ? average.substring(0, 3) : average;
        average = average.length() == 3 && average.charAt(2) == '0' ? average.substring(0, 1) : average;
        mView.showAverageRating(average, MAXIMUM_RATING);
    }

    public void onHomeButtonPressed() {
        mView.closeScreen();
    }
}
