package ru.arturvasilov.stackexchangeclient.model.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Tag implements Parcelable {

    @SerializedName("name")
    private final String mName;

    private boolean mIsFavourite;

    public Tag(@NonNull Parcel parcel) {
        mName = parcel.readString();
        boolean[] values = new boolean[1];
        parcel.readBooleanArray(values);
        mIsFavourite = values[0];
    }

    public Tag(@NonNull String name, boolean isFavourite) {
        mName = name;
        mIsFavourite = isFavourite;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public boolean isFavourite() {
        return mIsFavourite;
    }

    public void setFavourite(boolean favourite) {
        mIsFavourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeBooleanArray(new boolean[]{mIsFavourite});
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {

        @NonNull
        @Override
        public Tag createFromParcel(Parcel parcel) {
            return new Tag(parcel);
        }

        @NonNull
        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };
}
