package ru.arturvasilov.stackexchangeclient.view;

import android.support.annotation.NonNull;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.model.content.Question;

/**
 * @author Artur Vasilov
 */
public interface QuestionView {

    void showQuestion(@NonNull Question question);

    void showAnswers(@NonNull List<Answer> answers);

}
