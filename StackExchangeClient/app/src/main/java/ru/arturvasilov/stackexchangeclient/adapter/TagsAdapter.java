package ru.arturvasilov.stackexchangeclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.adapter.viewholder.TagsViewHolder;
import ru.arturvasilov.stackexchangeclient.model.content.Tag;

/**
 * @author Artur Vasilov
 */
public class TagsAdapter extends RecyclerView.Adapter<TagsViewHolder> {

    private final List<Tag> mTags;

    private final OnFavouriteClickListener mOnFavouriteClickListener;

    private final View.OnClickListener mInternalListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            mOnFavouriteClickListener.onFavouriteIconClick(position);
        }
    };

    public TagsAdapter(@NonNull OnFavouriteClickListener onFavouriteClickListener) {
        mOnFavouriteClickListener = onFavouriteClickListener;
        mTags = new ArrayList<>();
    }

    public void changeDataSet(@NonNull List<Tag> tags) {
        mTags.clear();
        mTags.addAll(tags);
        notifyDataSetChanged();
    }

    @Override
    public TagsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TagsViewHolder(inflater.inflate(R.layout.tag_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TagsViewHolder holder, int position) {
        Tag tag = mTags.get(position);
        holder.bind(tag, position, mInternalListener);
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public interface OnFavouriteClickListener {

        void onFavouriteIconClick(int position);

    }
}
