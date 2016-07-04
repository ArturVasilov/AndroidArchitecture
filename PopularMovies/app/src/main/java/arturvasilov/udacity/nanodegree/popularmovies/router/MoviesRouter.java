package arturvasilov.udacity.nanodegree.popularmovies.router;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import arturvasilov.udacity.nanodegree.popularmovies.activity.MovieDetailsActivity;
import arturvasilov.udacity.nanodegree.popularmovies.activity.SettingsActivity;
import arturvasilov.udacity.nanodegree.popularmovies.model.Movie;

/**
 * @author Artur Vasilov
 */
public class MoviesRouter {

    private final Activity mActivity;

    public MoviesRouter(@NonNull Activity activity) {
        mActivity = activity;
    }

    public void navigateToMovieScreen(@NonNull ImageView imageView, @NonNull Movie movie) {
        AppCompatActivity compatActivity = (AppCompatActivity) mActivity;
        MovieDetailsActivity.navigate(compatActivity, imageView, movie);
    }

    public void navigateToSettingsActivity() {
        Intent intent = new Intent(mActivity, SettingsActivity.class);
        mActivity.startActivity(intent);
    }

}
