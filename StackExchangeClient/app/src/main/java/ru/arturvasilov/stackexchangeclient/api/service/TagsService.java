package ru.arturvasilov.stackexchangeclient.api.service;

import android.support.annotation.NonNull;

import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.arturvasilov.stackexchangeclient.model.response.TagResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface TagsService {

    @GET("tags?order=desc&sort=popular&pagesize=50")
    Observable<TagResponse> searchTags(@NonNull @Query("inname") String search);

}
