package ru.itis.lectures.githubmvp.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Repository {

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("language")
    private String mLanguage;

    @SerializedName("stargazers_count")
    private int mStarsCount;

    @SerializedName("forks_count")
    private int mForksCount;

    @SerializedName("watchers_count")
    private int mWatchersCount;

    public Repository() {
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name == null ? "" : name;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description == null ? "" : description;
    }

    @NonNull
    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language == null ? "" : language;
    }

    public int getStarsCount() {
        return mStarsCount;
    }

    public void setStarsCount(int starsCount) {
        mStarsCount = starsCount < 0 ? 0 : starsCount;
    }

    public int getForksCount() {
        return mForksCount;
    }

    public void setForksCount(int forksCount) {
        mForksCount = forksCount < 0 ? 0 : forksCount;
    }

    public int getWatchersCount() {
        return mWatchersCount;
    }

    public void setWatchersCount(int watchersCount) {
        mWatchersCount = watchersCount < 0 ? 0 : watchersCount;
    }
}
