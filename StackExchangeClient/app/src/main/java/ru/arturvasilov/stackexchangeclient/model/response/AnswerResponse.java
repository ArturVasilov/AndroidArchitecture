package ru.arturvasilov.stackexchangeclient.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Answer;

/**
 * @author Artur Vasilov
 */
public class AnswerResponse extends ApiError {

    @SerializedName("items")
    private List<Answer> mAnswers;

    @NonNull
    public List<Answer> getAnswers() {
        if (mAnswers == null) {
            mAnswers = new ArrayList<>();
        }
        return mAnswers;
    }

    public void setAnswers(@NonNull List<Answer> answers) {
        mAnswers = answers;
    }
}
