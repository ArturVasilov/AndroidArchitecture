package ru.arturvasilov.stackexchangeclient.data.database;

import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.SQLiteConfig;
import ru.arturvasilov.sqlite.core.SQLiteContentProvider;
import ru.arturvasilov.sqlite.core.Schema;

/**
 * @author Artur Vasilov
 */
public class SQLiteProvider extends SQLiteContentProvider {

    private static final String DATABASE_NAME = "ru.arturvasilov.stackexchangeclient.database";
    private static final String CONTENT_AUTHORITY = "ru.arturvasilov.stackexchangeclient";

    @Override
    protected void prepareConfig(@NonNull SQLiteConfig config) {
        config.setDatabaseName(DATABASE_NAME);
        config.setAuthority(CONTENT_AUTHORITY);
    }

    @Override
    protected void prepareSchema(@NonNull Schema schema) {
        schema.register(UserTable.TABLE);
        schema.register(QuestionTable.TABLE);
        schema.register(AnswerTable.TABLE);
        schema.register(TagTable.TABLE);
        schema.register(NotificationTable.TABLE);
    }
}
