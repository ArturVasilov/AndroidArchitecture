package ru.arturvasilov.stackexchangeclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.adapter.viewholder.AnswerViewHolder;
import ru.arturvasilov.stackexchangeclient.model.content.Answer;

/**
 * @author Artur Vasilov
 */
public class AnswersAdapter extends RecyclerView.Adapter<AnswerViewHolder> {

    private final List<Answer> mAnswers;
    private final OnItemClickListener mOnItemClickListener;

    private final View.OnClickListener mInternalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            mOnItemClickListener.onItemClick(mAnswers.get(position));
        }
    };

    public AnswersAdapter(@NonNull OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        mAnswers = new ArrayList<>();
    }

    public void changeDataSet(@NonNull List<Answer> answers) {
        mAnswers.clear();
        mAnswers.addAll(answers);
        notifyDataSetChanged();
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new AnswerViewHolder(inflater.inflate(R.layout.answer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {
        holder.itemView.setOnClickListener(mInternalListener);
        holder.itemView.setTag(position);
        int maxLength = holder.itemView.getResources().getInteger(R.integer.body_preview_length);
        holder.bind(mAnswers.get(position), position == mAnswers.size() - 1, maxLength);
    }

    @Override
    public int getItemCount() {
        return mAnswers.size();
    }

    public interface OnItemClickListener {

        void onItemClick(@NonNull Answer answer);

    }
}
