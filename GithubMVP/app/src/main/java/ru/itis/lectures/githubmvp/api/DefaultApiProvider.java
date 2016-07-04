package ru.itis.lectures.githubmvp.api;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.itis.lectures.githubmvp.BuildConfig;

/**
 * @author Artur Vasilov
 */
public class DefaultApiProvider implements ApiProvider {

    private final String mToken;

    private final GithubService mService;

    public DefaultApiProvider(@NonNull String token) {
        mToken = token;
        mService = createGithubService();
    }

    @NonNull
    @Override
    public GithubService provideGithubService() {
        return mService;
    }

    @NonNull
    private GithubService createGithubService() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(OkHttp.newClient(provideToken()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService.class);
    }

    @NonNull
    @Override
    public String provideToken() {
        return mToken;
    }
}
