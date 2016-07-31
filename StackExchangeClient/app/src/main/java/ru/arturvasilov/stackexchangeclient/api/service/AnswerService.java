package ru.arturvasilov.stackexchangeclient.api.service;

import android.support.annotation.NonNull;

import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.arturvasilov.stackexchangeclient.model.response.AnswerResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface AnswerService {

    @NonNull
    @GET("/users/{ids}/answers?order=desc&sort=creation&pagesize=100&filter=withbody")
    Observable<AnswerResponse> answers(@Path("ids") int userId);

    @NonNull
    @GET("/questions/{ids}/answers?order=desc&sort=creation&filter=withbody")
    Observable<AnswerResponse> questionAnswers(@Path("ids") int questionId);

}
