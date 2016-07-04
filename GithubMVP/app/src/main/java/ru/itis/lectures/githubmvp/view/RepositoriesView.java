package ru.itis.lectures.githubmvp.view;

import android.support.annotation.NonNull;

import java.util.List;

import ru.itis.lectures.githubmvp.content.Repository;

/**
 * @author Artur Vasilov
 */
public interface RepositoriesView extends LoadingView {

    void showRepositories(@NonNull List<Repository> repositories);

    void showCommits(@NonNull Repository repository);
}
