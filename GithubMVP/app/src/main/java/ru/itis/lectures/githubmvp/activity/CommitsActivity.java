package ru.itis.lectures.githubmvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.api.ApiFactory;
import ru.itis.lectures.githubmvp.content.CommitResponse;
import ru.itis.lectures.githubmvp.content.Repository;
import ru.itis.lectures.githubmvp.content.Settings;
import ru.itis.lectures.githubmvp.rx.RxLoader;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class CommitsActivity extends AppCompatActivity {

    private static final String REPO_NAME_KEY = "repo_name_key";

    public static void start(@NonNull Activity activity, @NonNull Repository repository) {
        Intent intent = new Intent(activity, CommitsActivity.class);
        intent.putExtra(REPO_NAME_KEY, repository.getName());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userName = Settings.getUserName(this);
        String repositoryName = getIntent().getStringExtra(REPO_NAME_KEY);

        RxLoader.create(this, getLoaderManager(), R.id.commits_loader,
                ApiFactory.getProvider().provideGithubService()
                        .commits(userName, repositoryName)
                        .flatMap(Observable::from)
                        .map(CommitResponse::getCommit)
                        .toList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()))
                .init(commits -> {});
    }
}
