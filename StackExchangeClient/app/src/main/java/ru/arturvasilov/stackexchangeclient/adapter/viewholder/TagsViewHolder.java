package ru.arturvasilov.stackexchangeclient.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.model.content.Tag;
import ru.arturvasilov.stackexchangeclient.utils.Views;

/**
 * @author Artur Vasilov
 */
public class TagsViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTagName;
    private final ImageView mFavouriteIcon;

    public TagsViewHolder(View itemView) {
        super(itemView);
        mTagName = Views.findById(itemView, R.id.tagText);
        mFavouriteIcon = Views.findById(itemView, R.id.tagImage);
    }

    public void bind(@NonNull Tag tag, int position, @NonNull View.OnClickListener onFavouriteListener) {
        mTagName.setText(tag.getName());
        mFavouriteIcon.setImageResource(tag.isFavourite() ? R.drawable.ic_favorite : R.drawable.ic_not_favourite);
        mFavouriteIcon.setTag(position);
        mFavouriteIcon.setOnClickListener(onFavouriteListener);
    }
}
