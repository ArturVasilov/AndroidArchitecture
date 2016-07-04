package ru.itis.lectures.githubmvp.database.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.table.BaseTable;
import ru.arturvasilov.sqlite.table.Table;
import ru.arturvasilov.sqlite.table.TableBuilder;
import ru.itis.lectures.githubmvp.content.Commit;

/**
 * @author Artur Vasilov
 */
public final class CommitsTable extends BaseTable<Commit> {

    public static final Table<Commit> TABLE = new CommitsTable();

    public static final String REPOSITORY_NAME = "repository_name";
    public static final String AUTHOR = "author";
    public static final String MESSAGE = "message";

    private CommitsTable() {
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .stringColumn(REPOSITORY_NAME)
                .stringColumn(AUTHOR)
                .stringColumn(MESSAGE)
                .execute(database);
    }

    @Override
    public int getLastUpgradeVersion() {
        return 1;
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull Commit commit) {
        ContentValues values = new ContentValues();
        values.put(REPOSITORY_NAME, commit.getRepoName());
        values.put(AUTHOR, commit.getAuthor());
        values.put(MESSAGE, commit.getMessage());
        return values;
    }

    @NonNull
    @Override
    public Commit fromCursor(@NonNull Cursor cursor) {
        String repoName = cursor.getString(cursor.getColumnIndex(REPOSITORY_NAME));
        String author = cursor.getString(cursor.getColumnIndex(AUTHOR));
        String message = cursor.getString(cursor.getColumnIndex(MESSAGE));
        return new Commit(repoName, author, message);
    }
}
