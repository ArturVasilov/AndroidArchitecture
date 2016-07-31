package ru.arturvasilov.stackexchangeclient.model.content;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Artur Vasilov
 */
public class UserTag {

    @SerializedName("tag_name")
    private String mTagName;

    @SerializedName("question_count")
    private int mQuestionCount;

    @SerializedName("answer_count")
    private int mAnswerCount;

    @NonNull
    public String getTagName() {
        return mTagName;
    }

    public void setTagName(@NonNull String tagName) {
        mTagName = tagName;
    }

    public int getQuestionCount() {
        return mQuestionCount;
    }

    public void setQuestionCount(int questionCount) {
        mQuestionCount = questionCount;
    }

    public int getAnswerCount() {
        return mAnswerCount;
    }

    public void setAnswerCount(int answerCount) {
        mAnswerCount = answerCount;
    }
}
