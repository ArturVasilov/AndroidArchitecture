package ru.itis.lectures.githubmvp.testutils;

import android.support.annotation.NonNull;

import ru.itis.lectures.githubmvp.api.ApiProvider;
import ru.itis.lectures.githubmvp.api.GithubService;

/**
 * @author Artur Vasilov
 */
public class TestProviderImpl implements ApiProvider {

    private final GithubService mService;

    public TestProviderImpl(@NonNull GithubService service) {
        mService = service;
    }

    @NonNull
    @Override
    public GithubService provideGithubService() {
        return mService;
    }

    @NonNull
    @Override
    public String provideToken() {
        return "";
    }
}
