package ru.arturvasilov.stackexchangeclient.api.service;

import android.support.annotation.NonNull;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.arturvasilov.stackexchangeclient.model.response.QuestionResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface QuestionService {

    @NonNull
    @GET("/questions?order=desc&sort=creation&pagesize=50")
    Observable<QuestionResponse> questions(@Query("todate") long toDate);

    @NonNull
    @GET("/me/questions?order=desc&sort=creation&pagesize=50")
    Observable<QuestionResponse> myQuestions(@Query("todate") long toDate);

    @NonNull
    @GET("/questions?order=desc&sort=creation&pagesize=50")
    Observable<QuestionResponse> questions(@NonNull @Query("tagged") String tag, @Query("todate") long toDate);

    @NonNull
    @GET("/questions/{ids}?order=desc&sort=activity&filter=withbody")
    Observable<QuestionResponse> questionWithBody(@Path("ids") int questionId);
}
