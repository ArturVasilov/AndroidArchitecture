package ru.arturvasilov.stackexchangeclient.testutils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.data.database.LocalRepository;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.model.content.Tag;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class TestLocalRepository extends LocalRepository {

    @NonNull
    @Override
    public Observable<User> getCurrentUser() {
        User user = new User();
        user.setUserId(932123);
        user.setAge(19);
        user.setName("Artur Vasilov");
        user.setReputation(2985);
        user.setLink("http://stackoverflow.com/users/3637200/vasilov-artur");
        user.setProfileImage("https://i.stack.imgur.com/EJNBv.jpg?s=512&g=1");

        return Observable.just(user);
    }

    @NonNull
    @Override
    public Observable<List<Question>> questions(@NonNull String tag) {
        return Observable.just(new ArrayList<>());
    }

    @NonNull
    @Override
    public Observable<List<String>> tags() {
        return Observable.just(new ArrayList<>());
    }

    @Override
    public boolean updateTag(@NonNull Tag tag) {
        return true;
    }
}
