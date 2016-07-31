package ru.arturvasilov.stackexchangeclient.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.model.content.Answer;
import ru.arturvasilov.stackexchangeclient.utils.HtmlCompat;
import ru.arturvasilov.stackexchangeclient.utils.PicassoUtils;
import ru.arturvasilov.stackexchangeclient.utils.Views;

/**
 * @author Artur Vasilov
 */
public class AnswerViewHolder extends RecyclerView.ViewHolder {

    private final ImageView mAuthorIcon;
    private final TextView mAuthorName;
    private final TextView mAnswerBody;
    private final View mAnsweredIcon;
    private final View mDivider;

    public AnswerViewHolder(View itemView) {
        super(itemView);

        mAuthorIcon = Views.findById(itemView, R.id.icon);
        mAuthorName = Views.findById(itemView, R.id.authorName);
        mAnswerBody = Views.findById(itemView, R.id.answerBody);
        mAnsweredIcon = Views.findById(itemView, R.id.answeredView);
        mDivider = Views.findById(itemView, R.id.divider);
    }

    public void bind(@NonNull Answer answer, boolean isLast, int maxBodyLength) {
        Picasso.with(mAuthorIcon.getContext())
                .load(answer.getOwner().getProfileImage())
                .error(R.mipmap.ic_icon)
                .transform(PicassoUtils.circleTransform())
                .into(mAuthorIcon);

        mAuthorName.setText(answer.getOwner().getName());
        CharSequence body = HtmlCompat.fromHtml(answer.getBody());
        if (maxBodyLength > 0 && body.length() > maxBodyLength) {
            body = TextUtils.concat(body.subSequence(0, maxBodyLength - 3), "...");
        }
        mAnswerBody.setText(body);
        mAnsweredIcon.setVisibility(answer.isAccepted() ? View.VISIBLE : View.INVISIBLE);
        mDivider.setVisibility(isLast ? View.GONE : View.VISIBLE);
    }
}
