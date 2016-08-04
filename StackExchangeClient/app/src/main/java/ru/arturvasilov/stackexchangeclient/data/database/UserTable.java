package ru.arturvasilov.stackexchangeclient.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.arturvasilov.stackexchangeclient.model.content.User;

/**
 * @author Artur Vasilov
 */
public class UserTable extends BaseTable<User> {

    public static final Table<User> TABLE = new UserTable();

    public static final String USER_ID = "user_id";
    public static final String AGE = "age";
    public static final String NAME = "name";
    public static final String REPUTATION = "reputation";
    public static final String LINK = "link";
    public static final String PROFILE_IMAGE = "profile_image";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .primaryKey(USER_ID)
                .intColumn(USER_ID)
                .intColumn(AGE)
                .stringColumn(NAME)
                .intColumn(REPUTATION)
                .stringColumn(LINK)
                .stringColumn(PROFILE_IMAGE)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull User user) {
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(AGE, user.getAge());
        values.put(NAME, user.getName());
        values.put(REPUTATION, user.getReputation());
        values.put(LINK, user.getLink());
        values.put(PROFILE_IMAGE, user.getProfileImage());
        return values;
    }

    @NonNull
    @Override
    public User fromCursor(@NonNull Cursor cursor) {
        User user = new User();
        user.setUserId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
        user.setAge(cursor.getInt(cursor.getColumnIndex(AGE)));
        user.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        user.setReputation(cursor.getInt(cursor.getColumnIndex(REPUTATION)));
        user.setLink(cursor.getString(cursor.getColumnIndex(LINK)));
        user.setProfileImage(cursor.getString(cursor.getColumnIndex(PROFILE_IMAGE)));
        return user;
    }
}
