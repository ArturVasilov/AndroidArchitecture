package ru.arturvasilov.stackexchangeclient.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Question;

/**
 * @author Artur Vasilov
 */
public class QuestionResponse extends ApiError {

    @SerializedName("items")
    private List<Question> mQuestions;

    @NonNull
    public List<Question> getQuestions() {
        if (mQuestions == null) {
            mQuestions = new ArrayList<>();
        }
        return mQuestions;
    }

    public void setQuestions(@NonNull List<Question> questions) {
        mQuestions = questions;
    }
}
