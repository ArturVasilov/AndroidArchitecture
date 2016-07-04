package ru.itis.lectures.githubmvp.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class Commit {

    private String mRepoName;

    @SerializedName("author")
    private Author mAuthor = new Author();

    @SerializedName("message")
    private String mMessage;

    public Commit() {
    }

    public Commit(@NonNull String repoName, @NonNull String author, @NonNull String message) {
        mRepoName = repoName;
        mAuthor.mAuthorName = author;
        mMessage = message;
    }

    @NonNull
    public String getRepoName() {
        return mRepoName;
    }

    @NonNull
    public String getAuthor() {
        return mAuthor.mAuthorName;
    }

    public String getMessage() {
        return mMessage;
    }

    private class Author {

        @SerializedName("name")
        private String mAuthorName;

    }

}
