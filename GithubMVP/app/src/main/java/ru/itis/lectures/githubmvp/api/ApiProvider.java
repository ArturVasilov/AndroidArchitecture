package ru.itis.lectures.githubmvp.api;

import android.support.annotation.NonNull;

/**
 * @author Artur Vasilov
 */
public interface ApiProvider {

    @NonNull
    GithubService provideGithubService();

    @NonNull
    String provideToken();

}
