package arturvasilov.udacity.nanodegree.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Movie implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("original_title")
    private String mTitle;

    @SerializedName("release_date")
    private String mReleasedDate;

    @SerializedName("vote_average")
    private double mVoteAverage;

    public Movie() {
    }

    public Movie(Parcel in) {
        mId = in.readInt();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mTitle = in.readString();
        mReleasedDate = in.readString();
        mVoteAverage = in.readDouble();
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getPosterPath() {
        return mPosterPath;
    }

    @NonNull
    public String getOverview() {
        return mOverview;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getReleasedDate() {
        return mReleasedDate;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mPosterPath);
        parcel.writeString(mOverview);
        parcel.writeString(mTitle);
        parcel.writeString(mReleasedDate);
        parcel.writeDouble(mVoteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @NonNull
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @NonNull
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };
}
