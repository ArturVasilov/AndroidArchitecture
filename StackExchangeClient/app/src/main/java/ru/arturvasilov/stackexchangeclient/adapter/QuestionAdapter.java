package ru.arturvasilov.stackexchangeclient.adapter;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.adapter.viewholder.AnswerViewHolder;
import ru.arturvasilov.stackexchangeclient.adapter.viewholder.QuestionItemViewHolder;
import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.model.content.Question;

/**
 * @author Artur Vasilov
 */
public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Question mQuestion;
    private final List<Answer> mAnswers;

    public QuestionAdapter() {
        mAnswers = new ArrayList<>();
    }

    public void setQuestion(@NonNull Question question) {
        mQuestion = question;
        notifyDataSetChanged();
    }

    public void changeDataSet(@NonNull List<Answer> answers) {
        mAnswers.clear();
        mAnswers.addAll(answers);
        notifyDataSetChanged();
    }

    @ViewType
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ViewType.QUESTION_VIEW_TYPE;
        }
        return ViewType.ANSWER_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, @ViewType int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ViewType.QUESTION_VIEW_TYPE && mQuestion != null) {
            return new QuestionItemViewHolder(inflater.inflate(R.layout.question_item, parent, false));
        }
        return new AnswerViewHolder(inflater.inflate(R.layout.answer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0 && mQuestion != null) {
            ((QuestionItemViewHolder) holder).bind(mQuestion, 0, mAnswers.isEmpty(), null);
        } else {
            ((AnswerViewHolder) holder).bind(mAnswers.get(position - 1), position == mAnswers.size(), -1);
        }
        holder.itemView.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return (mQuestion == null ? 0 : 1) + mAnswers.size();
    }

    @IntDef({ViewType.QUESTION_VIEW_TYPE, ViewType.ANSWER_VIEW_TYPE})
    private @interface ViewType {
        int QUESTION_VIEW_TYPE = 1;
        int ANSWER_VIEW_TYPE = 2;
    }
}
