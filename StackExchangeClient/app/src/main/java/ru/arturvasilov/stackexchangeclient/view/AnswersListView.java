package ru.arturvasilov.stackexchangeclient.view;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Answer;

/**
 * @author Artur Vasilov
 */
public interface AnswersListView extends EmptyListView {

    void showAnswers(@NonNull List<Answer> answers);

    void setEmptyText(@StringRes int textResId);

    void browseUrl(@NonNull String url);

}
