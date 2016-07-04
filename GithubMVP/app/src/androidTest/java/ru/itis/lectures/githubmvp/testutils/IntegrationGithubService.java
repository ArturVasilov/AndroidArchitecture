package ru.itis.lectures.githubmvp.testutils;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;
import ru.itis.lectures.githubmvp.api.GithubService;
import ru.itis.lectures.githubmvp.content.CommitResponse;
import ru.itis.lectures.githubmvp.content.Repository;
import ru.itis.lectures.githubmvp.content.auth.Authorization;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class IntegrationGithubService implements GithubService {

    @Override
    public Observable<Authorization> authorize(@Header("Authorization") String authorization, @Body JsonObject params) {
        return Observable.empty();
    }

    @Override
    public Observable<List<Repository>> repositories() {
        return Observable.empty();
    }

    @Override
    public Observable<List<CommitResponse>> commits(@Path("user") String user, @Path("repo") String repo) {
        return Observable.empty();
    }
}
