package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils.Images;

/**
 * @author Artur Vasilov
 */
public class MovieDetailsViewModel extends BaseObservable {

    private static final String MAXIMUM_RATING = "10";

    private final Movie mMovie;

    public MovieDetailsViewModel(@NonNull Movie movie) {
        mMovie = movie;
    }

    @NonNull
    @Bindable
    public String getImageWidth() {
        return Images.WIDTH_780;
    }

    @NonNull
    @Bindable
    public String getYear() {
        return mMovie.getReleasedDate().substring(0, 4);
    }

    @NonNull
    @Bindable
    public String getVoteAverage() {
        String average = String.valueOf(mMovie.getVoteAverage());
        average = average.length() > 3 ? average.substring(0, 3) : average;
        average = average.length() == 3 && average.charAt(2) == '0' ? average.substring(0, 1) : average;
        return average;
    }

    @NonNull
    @Bindable
    public String getVoteMax() {
        return MAXIMUM_RATING;
    }

    @ColorRes
    @Bindable
    public int getExpandedTitleColor() {
        return android.R.color.transparent;
    }
}
