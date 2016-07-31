package ru.arturvasilov.stackexchangeclient.testutils.service;

import android.support.annotation.NonNull;

import retrofit2.http.Query;
import ru.arturvasilov.stackexchangeclient.api.service.TagsService;
import ru.arturvasilov.stackexchangeclient.model.response.TagResponse;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class TagsServiceMock implements TagsService {

    @Override
    public Observable<TagResponse> searchTags(@NonNull @Query("inname") String search) {
        return Observable.empty();
    }
}
