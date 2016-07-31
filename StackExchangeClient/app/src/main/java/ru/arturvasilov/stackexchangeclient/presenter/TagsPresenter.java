package ru.arturvasilov.stackexchangeclient.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.api.RepositoryProvider;
import ru.arturvasilov.stackexchangeclient.model.content.Tag;
import ru.arturvasilov.stackexchangeclient.rx.RxDecor;
import ru.arturvasilov.stackexchangeclient.rx.StubAction;
import ru.arturvasilov.stackexchangeclient.rx.rxloader.RxLoader;
import ru.arturvasilov.stackexchangeclient.utils.TextUtils;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import ru.arturvasilov.stackexchangeclient.view.TagsView;
import rx.Observable;

/**
 * @author Artur Vasilov
 */
public class TagsPresenter {

    private static final String FOUND_TAGS_KEY = "found_tags";
    private static final String FIRST_VISIBLE_ITEM_KEY = "first_visible_item_key";

    private final Context mContext;
    private final LoaderManager mLm;
    private final TagsView mView;
    private final LoadingView mLoadingView;

    private final List<Tag> mFavouriteTags;
    private final List<Tag> mFoundTags;

    private boolean mIsFavouriteShown;

    public TagsPresenter(@NonNull Context context, @NonNull LoaderManager lm, @NonNull TagsView view,
                         @NonNull LoadingView loadingView) {
        mContext = context;
        mLm = lm;
        mView = view;
        mLoadingView = loadingView;

        mFavouriteTags = new ArrayList<>();
        mFoundTags = new ArrayList<>();
    }

    public void init(@Nullable Bundle savedInstanceState) {
        mView.setEmptyText(R.string.no_tags_found);

        if (savedInstanceState != null) {
            List<Tag> tags = savedInstanceState.getParcelableArrayList(FOUND_TAGS_KEY);
            tags = tags == null ? new ArrayList<>() : tags;
            mFoundTags.addAll(tags);
        }
        final int firstVisibleItemPosition = savedInstanceState == null ? 0 : savedInstanceState.getInt(FIRST_VISIBLE_ITEM_KEY);

        RepositoryProvider.provideLocalRepository()
                .tags()
                .flatMap(Observable::from)
                .toSortedList()
                .flatMap(Observable::from)
                .map(tag -> new Tag(tag, true))
                .toList()
                .subscribe(tags -> {
                    handleInit(tags, firstVisibleItemPosition);
                }, new StubAction<>());
    }

    public void onClearButtonClick() {
        mView.clearText();
        onInput("");
    }

    public void onInput(@NonNull String input) {
        if (TextUtils.isEmpty(input)) {
            if (mFavouriteTags.isEmpty()) {
                mView.hideAllElements();
            } else {
                mView.showTags(mFavouriteTags);
                mView.hideEmptyListView();
                mView.hideClearButton();
                mLoadingView.hideLoadingIndicator();
                mIsFavouriteShown = true;
            }
            return;
        }
        mView.showClearButton();

        final String search = input.toLowerCase();
        Observable<List<Tag>> tagsObservable = RepositoryProvider.provideRemoteRepository()
                .searchTags(search)
                .flatMap(Observable::from)
                .map(tag -> {
                    for (Tag favourite : mFavouriteTags) {
                        if (TextUtils.equals(favourite.getName(), tag.getName())) {
                            tag.setFavourite(true);
                            return tag;
                        }
                    }
                    return tag;
                })
                .toSortedList((tag, tag2) -> {
                    return tag.getName().compareTo(tag2.getName());
                })
                .compose(RxDecor.loading(mLoadingView));

        RxLoader.create(mContext, mLm, R.id.tags_loader_id, tagsObservable)
                .restart(this::handleFoundTags, throwable -> mView.showEmptyListView());
    }

    public void onFavouriteClick(int position) {
        Tag tag;
        if (mIsFavouriteShown) {
            tag = mFavouriteTags.get(position);
        } else {
            tag = mFoundTags.get(position);
        }
        boolean isFavourite = RepositoryProvider.provideLocalRepository().updateTag(tag);
        tag.setFavourite(isFavourite);
        mView.notifyChanged();
    }

    public void onSaveInstanceState(@NonNull Bundle outState, int firstVisibleItemPosition) {
        outState.putParcelableArrayList(FOUND_TAGS_KEY, new ArrayList<>(mFoundTags));
        outState.putInt(FIRST_VISIBLE_ITEM_KEY, firstVisibleItemPosition);
    }

    private void handleInit(@NonNull List<Tag> favouriteTags, int firstVisibleItemPosition) {
        if (!mFoundTags.isEmpty()) {
            mView.showTags(mFoundTags);
            mView.hideEmptyListView();
            mView.showClearButton();
            mView.showFirstVisibleItem(firstVisibleItemPosition);
            mIsFavouriteShown = false;
        } else if (favouriteTags.isEmpty()) {
            mView.hideAllElements();
        } else {
            mFavouriteTags.addAll(favouriteTags);
            mView.showTags(mFavouriteTags);
            mView.hideEmptyListView();
            mView.hideClearButton();
            mView.showFirstVisibleItem(firstVisibleItemPosition);
            mIsFavouriteShown = true;
        }
    }

    private void handleFoundTags(@NonNull List<Tag> tags) {
        mFoundTags.clear();
        mFoundTags.addAll(tags);
        if (mFoundTags.isEmpty()) {
            mView.showEmptyListView();
        } else {
            mView.hideEmptyListView();
            mView.showTags(mFoundTags);
            mIsFavouriteShown = false;
        }
    }
}
