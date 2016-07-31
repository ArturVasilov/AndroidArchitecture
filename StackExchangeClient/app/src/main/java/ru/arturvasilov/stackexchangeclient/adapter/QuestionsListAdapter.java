package ru.arturvasilov.stackexchangeclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.adapter.viewholder.QuestionItemViewHolder;
import ru.arturvasilov.stackexchangeclient.model.content.Question;

/**
 * @author Artur Vasilov
 */
public class QuestionsListAdapter extends RecyclerView.Adapter<QuestionItemViewHolder> {

    private final List<Question> mQuestions;

    private final OnItemClickListener mOnItemClickListener;

    private final View.OnClickListener mInternalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            mOnItemClickListener.onItemClick(mQuestions.get(position), view);
        }
    };

    public QuestionsListAdapter(@NonNull OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        mQuestions = new ArrayList<>();
    }

    public void changeDataSet(@NonNull List<Question> questions) {
        mQuestions.clear();
        mQuestions.addAll(questions);
        notifyDataSetChanged();
    }

    public void addNewValues(@NonNull List<Question> questions) {
        mQuestions.addAll(questions);
        notifyDataSetChanged();
    }

    @Override
    public QuestionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new QuestionItemViewHolder(inflater.inflate(R.layout.question_item, parent, false));
    }

    @Override
    public void onBindViewHolder(QuestionItemViewHolder holder, int position) {
        holder.itemView.setOnClickListener(mInternalListener);
        holder.itemView.setTag(position);
        Question question = mQuestions.get(position);
        holder.bind(question, position, position == mQuestions.size() - 1, mInternalListener);
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public interface OnItemClickListener {

        void onItemClick(@NonNull Question question, @NonNull View view);

    }
}
