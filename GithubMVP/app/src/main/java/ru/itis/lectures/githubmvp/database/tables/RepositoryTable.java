package ru.itis.lectures.githubmvp.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.itis.lectures.githubmvp.content.Repository;

/**
 * @author Artur Vasilov
 */
public final class RepositoryTable extends BaseTable<Repository> {

    public static final Table<Repository> TABLE = new RepositoryTable();

    public static final String NAME_COLUMN = "name";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String LANGUAGE_COLUMN = "language";
    public static final String STARS_COLUMN = "stars";
    public static final String FORKS_COLUMN = "forks";
    public static final String WATCHERS_COLUMN = "watchers";

    private RepositoryTable() {
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .stringColumn(NAME_COLUMN)
                .stringColumn(DESCRIPTION_COLUMN)
                .stringColumn(LANGUAGE_COLUMN)
                .intColumn(STARS_COLUMN)
                .intColumn(FORKS_COLUMN)
                .intColumn(WATCHERS_COLUMN)
                .execute(database);
    }

    @Override
    public int getLastUpgradeVersion() {
        return 1;
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull Repository object) {
        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN, object.getName());
        values.put(DESCRIPTION_COLUMN, object.getDescription());
        values.put(LANGUAGE_COLUMN, object.getLanguage());
        values.put(STARS_COLUMN, object.getStarsCount());
        values.put(FORKS_COLUMN, object.getForksCount());
        values.put(WATCHERS_COLUMN, object.getWatchersCount());
        return values;
    }

    @NonNull
    @Override
    public Repository fromCursor(@NonNull Cursor cursor) {
        Repository repository = new Repository();
        repository.setName(cursor.getString(cursor.getColumnIndex(NAME_COLUMN)));
        repository.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION_COLUMN)));
        repository.setLanguage(cursor.getString(cursor.getColumnIndex(LANGUAGE_COLUMN)));
        repository.setStarsCount(cursor.getInt(cursor.getColumnIndex(STARS_COLUMN)));
        repository.setForksCount(cursor.getInt(cursor.getColumnIndex(FORKS_COLUMN)));
        repository.setWatchersCount(cursor.getInt(cursor.getColumnIndex(WATCHERS_COLUMN)));
        return repository;
    }

}
