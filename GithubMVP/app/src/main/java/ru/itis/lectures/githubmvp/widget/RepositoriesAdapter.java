package ru.itis.lectures.githubmvp.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.content.Repository;

import static ru.itis.lectures.githubmvp.utils.Views.findById;

/**
 * @author Artur Vasilov
 */
public class RepositoriesAdapter extends BaseAdapter<RepositoriesAdapter.ViewHolder, Repository> {

    public RepositoriesAdapter(@NonNull List<Repository> items) {
        super(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repository_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Repository repository = getItem(position);
        holder.mName.setText(repository.getName());
        holder.mLanguage.setText(repository.getLanguage());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mName;
        private final TextView mLanguage;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = findById(itemView, R.id.repositoryName);
            mLanguage = findById(itemView, R.id.repositoryLanguage);
        }
    }

}
