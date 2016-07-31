package ru.arturvasilov.stackexchangeclient.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Badge {

    @SerializedName("name")
    private String mName;

    @SerializedName("link")
    private String mLink;

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    @NonNull
    public String getLink() {
        return mLink;
    }

    public void setLink(@NonNull String link) {
        mLink = link;
    }
}
