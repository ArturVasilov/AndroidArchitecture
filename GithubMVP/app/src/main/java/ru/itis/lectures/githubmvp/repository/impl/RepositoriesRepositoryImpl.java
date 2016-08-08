package ru.itis.lectures.githubmvp.repository.impl;

import android.support.annotation.NonNull;

import java.util.List;

import ru.arturvasilov.sqlite.core.SQLite;
import ru.arturvasilov.sqlite.rx.RxSQLite;
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
        return RxSQLite.get().query(RepositoryTable.TABLE);
    }

    @Override
    public void saveRepositories(@NonNull List<Repository> repositories) {
        SQLite.get().delete(RepositoryTable.TABLE);
        SQLite.get().insert(RepositoryTable.TABLE, repositories);
    }
}
