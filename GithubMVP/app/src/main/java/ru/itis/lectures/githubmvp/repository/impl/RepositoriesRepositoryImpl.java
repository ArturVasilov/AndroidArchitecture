package ru.itis.lectures.githubmvp.repository.impl;

import android.support.annotation.NonNull;

import java.util.List;

import ru.arturvasilov.sqlite.SQLite;
import ru.itis.lectures.githubmvp.content.Repository;
import ru.itis.lectures.githubmvp.database.tables.RepositoryTable;
import ru.itis.lectures.githubmvp.repository.RepositoriesRepository;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class RepositoriesRepositoryImpl implements RepositoriesRepository {

    @NonNull
    @Override
    public Observable<List<Repository>> cachedRepositories() {
        return SQLite.get()
                .query(RepositoryTable.TABLE)
                .all()
                .asObservable();
    }

    @Override
    public void saveRepositories(@NonNull List<Repository> repositories) {
        SQLite.get().delete(RepositoryTable.TABLE).execute();
        SQLite.get().insert(RepositoryTable.TABLE).insert(repositories);
    }
}
