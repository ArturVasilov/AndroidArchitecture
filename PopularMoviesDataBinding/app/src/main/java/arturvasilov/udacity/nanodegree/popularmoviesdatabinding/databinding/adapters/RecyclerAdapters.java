package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.databinding.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.Movie;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget.BaseAdapter;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget.EmptyRecyclerView;
import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.widget.MoviesAdapter;

/**
 * @author Artur Vasilov
 */
public class RecyclerAdapters {

    @BindingAdapter(value = {"app:layoutManager", "app:adapter", "app:empty", "app:onItemClick"}, requireAll = true)
    public static void initRecycler(@NonNull EmptyRecyclerView recycler, @NonNull RecyclerView.LayoutManager layoutManager,
                                    @NonNull BaseAdapter adapter, @NonNull View emptyView,
                                    @NonNull BaseAdapter.OnItemClickListener listener) {
        recycler.setLayoutManager(layoutManager);
        recycler.setEmptyView(emptyView);
        adapter.attachToRecyclerView(recycler);
        //noinspection unchecked
        adapter.setOnItemClickListener(listener);
    }

    @BindingAdapter("app:movies")
    public static void setMovies(@NonNull EmptyRecyclerView recycler, @NonNull List<Movie> movies) {
        MoviesAdapter adapter = (MoviesAdapter) recycler.getAdapter();
        if (adapter != null) {
            adapter.setNewValues(movies);
        }
    }

}
