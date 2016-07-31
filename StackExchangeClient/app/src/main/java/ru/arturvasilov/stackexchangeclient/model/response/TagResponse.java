package ru.arturvasilov.stackexchangeclient.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Tag;

/**
 * @author Artur Vasilov
 */
public class TagResponse extends ApiError {

    @SerializedName("items")
    private List<Tag> mTags;

    @NonNull
    public List<Tag> getTags() {
        if (mTags == null) {
            mTags = new ArrayList<>();
        }
        return mTags;
    }

    public void setTags(@NonNull List<Tag> tags) {
        mTags = tags;
    }
}
