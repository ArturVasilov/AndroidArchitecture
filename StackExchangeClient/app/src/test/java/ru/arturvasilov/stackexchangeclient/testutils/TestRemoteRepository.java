package ru.arturvasilov.stackexchangeclient.testutils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.api.RemoteRepository;
import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.model.content.Badge;
import ru.arturvasilov.stackexchangeclient.model.content.Notification;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.model.content.Tag;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.model.content.UserTag;
import ru.arturvasilov.stackexchangeclient.model.response.ApiError;
import ru.arturvasilov.stackexchangeclient.testutils.service.AnswerServiceMock;
import ru.arturvasilov.stackexchangeclient.testutils.service.ApplicationServiceMock;
import ru.arturvasilov.stackexchangeclient.testutils.service.NotificationServiceMock;
import ru.arturvasilov.stackexchangeclient.testutils.service.QuestionServiceMock;
import ru.arturvasilov.stackexchangeclient.testutils.service.TagsServiceMock;
import ru.arturvasilov.stackexchangeclient.testutils.service.UserInfoServiceMock;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class TestRemoteRepository extends RemoteRepository {

    public TestRemoteRepository() {
        super(new UserInfoServiceMock(), new QuestionServiceMock(), new AnswerServiceMock(),
                new TagsServiceMock(), new NotificationServiceMock(), new ApplicationServiceMock());
    }

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
    public Observable<List<Question>> moreQuestions(@NonNull String tag, long toDate) {
        return Observable.just(new ArrayList<>());
    }

    @NonNull
    @Override
    public Observable<List<Tag>> searchTags(@NonNull String search) {
        return Observable.just(new ArrayList<>());
    }

    @NonNull
    @Override
    public Observable<List<Badge>> badges(int userId) {
        return Observable.just(new ArrayList<>());
    }

    @NonNull
    @Override
    public Observable<List<UserTag>> topTags(int userId) {
        return Observable.just(new ArrayList<>());
    }

    @NonNull
    @Override
    public Observable<List<Answer>> answers(int userId) {
        return Observable.just(new ArrayList<>());
    }

    @NonNull
    @Override
    public Observable<Question> questionWithBody(int questionId) {
        return Observable.just(new Question());
    }

    @NonNull
    @Override
    public Observable<List<Answer>> questionAnswers(int questionId) {
        return Observable.just(new ArrayList<>());
    }

    @NonNull
    @Override
    public Observable<List<Notification>> notifications() {
        return Observable.just(new ArrayList<>());
    }

    @NonNull
    @Override
    public Observable<ApiError> logout(@NonNull String token) {
        return Observable.just(new ApiError());
    }
}
