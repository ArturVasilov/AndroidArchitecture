package ru.arturvasilov.stackexchangeclient.testutils.service;

import android.support.annotation.NonNull;

import retrofit2.http.Path;
import ru.arturvasilov.stackexchangeclient.api.service.AnswerService;
import ru.arturvasilov.stackexchangeclient.model.response.AnswerResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class AnswerServiceMock implements AnswerService {

    @NonNull
    @Override
    public Observable<AnswerResponse> answers(@Path("ids") int userId) {
        return Observable.empty();
    }

    @NonNull
    @Override
    public Observable<AnswerResponse> questionAnswers(@Path("ids") int questionId) {
        return Observable.empty();
    }
}
