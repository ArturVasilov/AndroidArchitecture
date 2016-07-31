package ru.arturvasilov.stackexchangeclient.data.database;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventKeys;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventTags;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.model.content.Tag;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.rx.RxSchedulers;
import ru.arturvasilov.stackexchangeclient.rx.StubAction;
import ru.arturvasilov.stackexchangeclient.sqlite.SQLite;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Artur Vasilov
 */
public class LocalRepository {

    private final SQLite mDb;

    public LocalRepository(@NonNull Context context) {
        mDb = SQLite.initialize(context);
    }

    @NonNull
    public Observable<User> getCurrentUser() {
        return RepositoryProvider.provideKeyValueStorage().getCurrentUserId()
                .take(1)
                .filter(id -> id > 0)
                .map(String::valueOf)
                .flatMap(id -> mDb.query(UserTable.TABLE)
                        .object()
                        .where(UserTable.USER_ID + "=?")
                        .whereArgs(new String[]{id})
                        .asObservable())
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<Question>> questions(@NonNull String tag) {
        return mDb.query(QuestionTable.TABLE)
                .all()
                .where(QuestionTable.TAG + "=?")
                .whereArgs(new String[]{tag})
                .asObservable()
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<String>> tags() {
        return mDb.query(TagTable.TABLE)
                .all()
                .asObservable()
                .compose(RxSchedulers.async());
    }

    public boolean updateTag(@NonNull Tag tag) {
        if (tag.isFavourite()) {
            SQLite.get().delete(TagTable.TABLE)
                    .where(TagTable.TAG + "=?")
                    .whereArgs(new String[]{tag.getName()})
                    .execute();
            Analytics.buildEvent()
                    .putString(EventKeys.TAG, tag.getName())
                    .log(EventTags.TAGS_REMOVE_FAVOURITE);
            return false;
        } else {
            SQLite.get().insert(TagTable.TABLE).insert(tag.getName());
            Analytics.buildEvent()
                    .putString(EventKeys.TAG, tag.getName())
                    .log(EventTags.TAGS_ADD_FAVOURITE);
            return true;
        }
    }

    public void logout() {
        Observable.just(true)
                .flatMap(value -> {
                    SQLite.get().delete(UserTable.TABLE).execute();
                    SQLite.get().delete(QuestionTable.TABLE).execute();
                    SQLite.get().delete(TagTable.TABLE).execute();
                    SQLite.get().delete(AnswerTable.TABLE).execute();
                    return Observable.just(value);
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new StubAction<>(), new StubAction<>());
    }
}
