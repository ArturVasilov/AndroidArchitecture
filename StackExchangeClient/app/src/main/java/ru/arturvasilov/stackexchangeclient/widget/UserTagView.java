package ru.arturvasilov.stackexchangeclient.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.TextView;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.model.content.UserTag;
import ru.arturvasilov.stackexchangeclient.utils.Views;

/**
 * @author Artur Vasilov
 */
@SuppressLint("ViewConstructor")
public class UserTagView extends FrameLayout {

    private final UserTag mTag;

    public UserTagView(Context context, @NonNull UserTag tag) {
        super(context);
        mTag = tag;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.top_tag_view, this);

        Views.<TextView>findById(this, R.id.tagName).setText(mTag.getTagName());
        String questionsCount = getContext().getString(R.string.questions_count, mTag.getQuestionCount());
        Views.<TextView>findById(this, R.id.questionsCount).setText(questionsCount);
        String answersCount = getContext().getString(R.string.answers_count, mTag.getAnswerCount());
        Views.<TextView>findById(this, R.id.answersCount).setText(answersCount);
    }

}
