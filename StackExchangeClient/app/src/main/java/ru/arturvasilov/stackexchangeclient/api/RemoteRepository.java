package ru.arturvasilov.stackexchangeclient.api;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.api.service.AnswerService;
import ru.arturvasilov.stackexchangeclient.api.service.ApplicationService;
import ru.arturvasilov.stackexchangeclient.api.service.NotificationService;
import ru.arturvasilov.stackexchangeclient.api.service.QuestionService;
import ru.arturvasilov.stackexchangeclient.api.service.TagsService;
import ru.arturvasilov.stackexchangeclient.api.service.UserInfoService;
import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.data.database.AnswerTable;
import ru.arturvasilov.stackexchangeclient.data.database.QuestionTable;
import ru.arturvasilov.stackexchangeclient.data.database.UserTable;
import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.model.content.Badge;
import ru.arturvasilov.stackexchangeclient.model.content.Notification;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.model.content.Tag;
import ru.arturvasilov.stackexchangeclient.model.content.User;
import ru.arturvasilov.stackexchangeclient.model.content.UserTag;
import ru.arturvasilov.stackexchangeclient.model.response.AnswerResponse;
import ru.arturvasilov.stackexchangeclient.model.response.ApiError;
import ru.arturvasilov.stackexchangeclient.model.response.BadgeResponse;
import ru.arturvasilov.stackexchangeclient.model.response.NotificationResponse;
import ru.arturvasilov.stackexchangeclient.model.response.QuestionResponse;
import ru.arturvasilov.stackexchangeclient.model.response.TagResponse;
import ru.arturvasilov.stackexchangeclient.model.response.UserResponse;
import ru.arturvasilov.stackexchangeclient.model.response.UserTagResponse;
import ru.arturvasilov.stackexchangeclient.rx.RxSchedulers;
import ru.arturvasilov.stackexchangeclient.sqlite.SQLite;
import ru.arturvasilov.stackexchangeclient.utils.TextUtils;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class RemoteRepository {

    private final UserInfoService mUserInfoService;
    private final QuestionService mQuestionService;
    private final AnswerService mAnswerService;
    private final TagsService mTagsService;
    private final NotificationService mNotificationService;
    private final ApplicationService mApplicationService;

    public RemoteRepository(@NonNull UserInfoService userInfoService, @NonNull QuestionService questionService,
                            @NonNull AnswerService answerService, @NonNull TagsService tagsService,
                            @NonNull NotificationService notificationService, @NonNull ApplicationService applicationService) {
        mUserInfoService = userInfoService;
        mQuestionService = questionService;
        mAnswerService = answerService;
        mTagsService = tagsService;
        mNotificationService = notificationService;
        mApplicationService = applicationService;
    }

    @NonNull
    public Observable<User> getCurrentUser() {
        return mUserInfoService.getCurrentUser()
                .compose(ErrorsHandler.handleErrors())
                .map(UserResponse::getUsers)
                .map(users -> users.get(0))
                .flatMap(user -> {
                    SQLite.get().insert(UserTable.TABLE).insert(user);
                    RepositoryProvider.provideKeyValueStorage().saveUserId(user.getUserId());
                    Analytics.setCurrentUser(user);
                    return Observable.just(user);
                })
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<Question>> questions(@NonNull String tag) {
        long toDate = (System.currentTimeMillis() + DateUtils.DAY_IN_MILLIS) / 1000;
        return buildQuestionsObservable(tag, toDate)
                .compose(ErrorsHandler.handleErrors())
                .map(QuestionResponse::getQuestions)
                .flatMap(Observable::from)
                .map(question -> {
                    question.setTag(tag);
                    return question;
                })
                .toList()
                .flatMap(questions -> {
                    SQLite.get().delete(QuestionTable.TABLE)
                            .where(QuestionTable.TAG + "=?")
                            .whereArgs(new String[]{tag})
                            .execute();
                    SQLite.get().insert(QuestionTable.TABLE).insert(questions);
                    return Observable.just(questions);
                })
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<Question>> moreQuestions(@NonNull String tag, long toDate) {
        return buildQuestionsObservable(tag, toDate)
                .compose(ErrorsHandler.handleErrors())
                .map(QuestionResponse::getQuestions)
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<Tag>> searchTags(@NonNull String search) {
        return mTagsService.searchTags(search)
                .compose(ErrorsHandler.handleErrors())
                .map(TagResponse::getTags)
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<Badge>> badges(int userId) {
        return mUserInfoService.badges(userId)
                .compose(ErrorsHandler.handleErrors())
                .map(BadgeResponse::getBadges)
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<UserTag>> topTags(int userId) {
        return mUserInfoService.topTags(userId)
                .compose(ErrorsHandler.handleErrors())
                .map(UserTagResponse::getUserTags)
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<Answer>> answers(int userId) {
        return mAnswerService.answers(userId)
                .compose(ErrorsHandler.handleErrors())
                .map(AnswerResponse::getAnswers)
                .flatMap(answers -> {
                    SQLite.get().delete(AnswerTable.TABLE).execute();
                    SQLite.get().insert(AnswerTable.TABLE).insert(answers);
                    return Observable.just(answers);
                })
                .onErrorResumeNext(throwable -> {
                    List<Answer> answers = SQLite.get().query(AnswerTable.TABLE).all().execute();
                    if (answers.isEmpty()) {
                        return Observable.error(throwable);
                    }
                    return Observable.just(answers);
                })
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<Question> questionWithBody(int questionId) {
        return mQuestionService.questionWithBody(questionId)
                .compose(ErrorsHandler.handleErrors())
                .map(QuestionResponse::getQuestions)
                .map(questions -> questions.get(0))
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<Answer>> questionAnswers(int questionId) {
        return mAnswerService.questionAnswers(questionId)
                .compose(ErrorsHandler.handleErrors())
                .map(AnswerResponse::getAnswers)
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<List<Notification>> notifications() {
        return mNotificationService.notifications()
                .compose(ErrorsHandler.handleErrors())
                .map(NotificationResponse::getNotifications)
                .compose(RxSchedulers.async());
    }

    @NonNull
    public Observable<ApiError> logout(@NonNull String token) {
        return mApplicationService.logout(token)
                .compose(ErrorsHandler.handleErrors())
                .compose(RxSchedulers.async());
    }

    @NonNull
    private Observable<QuestionResponse> buildQuestionsObservable(@NonNull String tag, long toDate) {
        Observable<QuestionResponse> questionsObservable;
        if (TextUtils.equals(ApiConstants.TAG_ALL, tag)) {
            questionsObservable = mQuestionService.questions(toDate);
        } else if (TextUtils.equals(ApiConstants.TAG_MY_QUESTIONS, tag)) {
            questionsObservable = mQuestionService.myQuestions(toDate);
        } else {
            questionsObservable = mQuestionService.questions(tag, toDate);
        }
        return questionsObservable;
    }
}

