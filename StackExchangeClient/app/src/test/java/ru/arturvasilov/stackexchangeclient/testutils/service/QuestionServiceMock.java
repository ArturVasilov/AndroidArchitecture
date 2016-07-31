package ru.arturvasilov.stackexchangeclient.testutils.service;

import android.support.annotation.NonNull;

import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.arturvasilov.stackexchangeclient.api.service.QuestionService;
import ru.arturvasilov.stackexchangeclient.model.response.QuestionResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class QuestionServiceMock implements QuestionService {

    @NonNull
    @Override
    public Observable<QuestionResponse> questions(@Query("todate") long toDate) {
        return Observable.empty();
    }

    @NonNull
    @Override
    public Observable<QuestionResponse> myQuestions(@Query("todate") long toDate) {
        return Observable.empty();
    }

    @NonNull
    @Override
    public Observable<QuestionResponse> questions(@NonNull @Query("tagged") String tag, @Query("todate") long toDate) {
        return Observable.empty();
    }

    @NonNull
    @Override
    public Observable<QuestionResponse> questionWithBody(@Path("ids") int questionId) {
        return Observable.empty();
    }
}
