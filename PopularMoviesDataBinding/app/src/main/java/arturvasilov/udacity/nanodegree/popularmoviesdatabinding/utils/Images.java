package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.AppDelegate;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.BuildConfig;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Movie;

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
        loadMovie(imageView, movie.getPosterPath(), size);
    }

    public static void loadMovie(@NonNull ImageView imageView, @NonNull String posterPath,
                                 @NonNull String size) {
        String url = BuildConfig.IMAGES_BASE_URL + size + posterPath;
        Picasso.with(imageView.getContext())
                .load(url)
                .noFade()
                .into(imageView);
    }

    public static void fetch(@NonNull String posterPath, @NonNull String size) {
        String url = BuildConfig.IMAGES_BASE_URL + size + posterPath;
        Picasso.with(AppDelegate.getAppContext())
                .load(url)
                .fetch();
    }
}
