package ru.arturvasilov.stackexchangeclient.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import ru.arturvasilov.sqlite.core.BaseTable;
import ru.arturvasilov.sqlite.core.Table;
import ru.arturvasilov.sqlite.utils.TableBuilder;
import ru.arturvasilov.stackexchangeclient.app.GsonHolder;
import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.model.content.User;

/**
 * @author Artur Vasilov
 */
public class AnswerTable extends BaseTable<Answer> {

    public static final Table<Answer> TABLE = new AnswerTable();

    private static final String OWNER = "owner";
    private static final String BODY = "body";
    private static final String IS_ACCEPTED = "is_accepted";
    private static final String QUESTION_ID = "question_id";
    private static final String ANSWER_ID = "answer_id";

    @Override
    public void onCreate(@NonNull SQLiteDatabase database) {
        TableBuilder.create(this)
                .stringColumn(OWNER)
                .stringColumn(BODY)
                .intColumn(IS_ACCEPTED)
                .intColumn(QUESTION_ID)
                .intColumn(ANSWER_ID)
                .primaryKey(QUESTION_ID, ANSWER_ID)
                .execute(database);
    }

    @NonNull
    @Override
    public ContentValues toValues(@NonNull Answer answer) {
        ContentValues values = new ContentValues();
        values.put(OWNER, GsonHolder.getGson().toJson(answer.getOwner()));
        values.put(BODY, answer.getBody());
        values.put(IS_ACCEPTED, answer.isAccepted() ? 1 : 0);
        values.put(QUESTION_ID, answer.getQuestionId());
        values.put(ANSWER_ID, answer.getAnswerId());
        return values;
    }

    @NonNull
    @Override
    public Answer fromCursor(@NonNull Cursor cursor) {
        User owner = GsonHolder.getGson().fromJson(cursor.getString(cursor.getColumnIndex(OWNER)), User.class);
        Answer answer = new Answer();
        answer.setOwner(owner);
        answer.setBody(cursor.getString(cursor.getColumnIndex(BODY)));
        answer.setAccepted(cursor.getInt(cursor.getColumnIndex(IS_ACCEPTED)) > 0);
        answer.setQuestionId(cursor.getInt(cursor.getColumnIndex(QUESTION_ID)));
        answer.setAnswerId(cursor.getInt(cursor.getColumnIndex(ANSWER_ID)));
        return answer;
    }
}
