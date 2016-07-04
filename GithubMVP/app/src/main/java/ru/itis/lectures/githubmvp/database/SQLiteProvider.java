package ru.itis.lectures.githubmvp.database;

import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.SQLiteConfig;
import ru.arturvasilov.sqlite.core.SQLiteContentProvider;
import ru.arturvasilov.sqlite.core.Schema;
import ru.itis.lectures.githubmvp.database.tables.CommitsTable;
import ru.itis.lectures.githubmvp.database.tables.RepositoryTable;

/**
 * @author Artur Vasilov
 */
public class SQLiteProvider extends SQLiteContentProvider {

    private static final String DATABASE_NAME = "ru.itis.lectures.githubmvp.database";
    private static final String CONTENT_AUTHORITY = "ru.itis.lectures.githubmvp";

    @Override
    protected void prepareConfig(@NonNull SQLiteConfig config) {
        config.setDatabaseName(DATABASE_NAME);
        config.setAuthority(CONTENT_AUTHORITY);
    }

    @Override
    protected void prepareSchema(@NonNull Schema schema) {
        schema.register(RepositoryTable.TABLE);
        schema.register(CommitsTable.TABLE);
    }
}
