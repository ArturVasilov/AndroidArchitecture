package arturvasilov.udacity.nanodegree.popularmovies.utils;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import arturvasilov.udacity.nanodegree.popularmovies.BuildConfig;
import arturvasilov.udacity.nanodegree.popularmovies.model.Movie;

/**
 * @author Artur Vasilov
 */
public final class Images {

    public static final String WIDTH_185 = "w185";
    public static final String WIDTH_780 = "w780";

    private Images() {
    }

    public static void loadMovie(@NonNull ImageView imageView, @NonNull Movie movie,
                                 @NonNull String size) {
        String url = BuildConfig.IMAGES_BASE_URL + size + movie.getPosterPath();
        Picasso.with(imageView.getContext())
                .load(url)
                .noFade()
                .into(imageView);
    }
}
