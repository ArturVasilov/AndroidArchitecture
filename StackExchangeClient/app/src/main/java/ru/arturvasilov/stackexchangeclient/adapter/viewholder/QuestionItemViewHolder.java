package ru.arturvasilov.stackexchangeclient.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.utils.HtmlCompat;
import ru.arturvasilov.stackexchangeclient.utils.PicassoUtils;
import ru.arturvasilov.stackexchangeclient.utils.TextUtils;
import ru.arturvasilov.stackexchangeclient.utils.Views;

/**
 * @author Artur Vasilov
 */
public class QuestionItemViewHolder extends RecyclerView.ViewHolder {

    private final ImageView mAuthorIcon;
    private final TextView mTitle;
    private final TextView mAuthorName;
    private final TextView mBody;
    private final TextView mViewsCount;
    private final TextView mAnswersCount;
    private final View mAnsweredIcon;
    private final View mDivider;

    public QuestionItemViewHolder(View itemView) {
        super(itemView);

        mAuthorIcon = Views.findById(itemView, R.id.icon);
        mTitle = Views.findById(itemView, R.id.questionTitle);
        mAuthorName = Views.findById(itemView, R.id.questionAuthor);
        mBody = Views.findById(itemView, R.id.questionBody);
        mViewsCount = Views.findById(itemView, R.id.viewsCount);
        mAnswersCount = Views.findById(itemView, R.id.answersCount);
        mAnsweredIcon = Views.findById(itemView, R.id.answeredQuestion);
        mDivider = Views.findById(itemView, R.id.divider);

        mBody.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void bind(@NonNull Question question, int position, boolean isLast,
                     @Nullable View.OnClickListener iconClickListener) {
        Picasso.with(mAuthorIcon.getContext())
                .load(question.getOwner().getProfileImage())
                .error(R.mipmap.ic_icon)
                .transform(PicassoUtils.circleTransform())
                .into(mAuthorIcon);
        mAuthorIcon.setOnClickListener(iconClickListener);
        mAuthorIcon.setTag(position);

        mTitle.setText(question.getTitle());
        mAuthorName.setText(question.getOwner().getName());
        if (!TextUtils.isEmpty(question.getBody())) {
            mBody.setVisibility(View.VISIBLE);
            mBody.setText(HtmlCompat.fromHtml(question.getBody()));
        }
        mViewsCount.setText(String.valueOf(question.getViewCount()));
        mAnswersCount.setText(String.valueOf(question.getAnswerCount()));
        mAnsweredIcon.setVisibility(question.isAnswered() ? View.VISIBLE : View.INVISIBLE);
        mDivider.setVisibility(isLast ? View.GONE : View.VISIBLE);
    }

}
