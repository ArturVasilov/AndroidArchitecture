package ru.arturvasilov.stackexchangeclient.activity;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.adapter.TagsAdapter;
import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventTags;
import ru.arturvasilov.stackexchangeclient.model.content.Tag;
import ru.arturvasilov.stackexchangeclient.presenter.TagsPresenter;
import ru.arturvasilov.stackexchangeclient.utils.Views;
import ru.arturvasilov.stackexchangeclient.view.LoadingView;
import ru.arturvasilov.stackexchangeclient.view.TagsView;

/**
 * @author Artur Vasilov
 */
public class TagsActivity extends AppCompatActivity implements TagsView, LoadingView {

    private EditText mSearchEdit;
    private View mClearButton;
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private ProgressBar mLoadingView;

    private TagsAdapter mAdapter;

    private TagsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_tags);
        setSupportActionBar(Views.findById(this, R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Analytics.buildEvent().log(EventTags.SCREEN_TAGS);
        mPresenter = new TagsPresenter(this, getLoaderManager(), this, this);

        mSearchEdit = Views.findById(this, R.id.searchEdit);
        mClearButton = Views.findById(this, R.id.btnClear);
        mRecyclerView = Views.findById(this, R.id.recyclerView);
        mEmptyView = Views.findById(this, R.id.empty);
        mLoadingView = Views.findById(this, R.id.progressBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLoadingView.setIndeterminateTintList(ContextCompat.getColorStateList(this, R.color.primary));
        } else {
            mLoadingView.getIndeterminateDrawable().setColorFilter(
                    ContextCompat.getColor(this, R.color.primary), PorterDuff.Mode.SRC_IN);
        }

        mClearButton.setOnClickListener(view -> mPresenter.onClearButtonClick());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TagsAdapter(mPresenter::onFavouriteClick);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.init(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchEdit.addTextChangedListener(new SearchListener());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mPresenter.onSaveInstanceState(outState, layoutManager.findFirstVisibleItemPosition());
    }

    @Override
    public void clearText() {
        mSearchEdit.setText("");
    }

    @Override
    public void setEmptyText(@StringRes int textId) {
        mEmptyView.setText(textId);
    }

    @Override
    public void showTags(@NonNull List<Tag> tags) {
        mAdapter.changeDataSet(tags);
    }

    @Override
    public void notifyChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFirstVisibleItem(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    @Override
    public void showClearButton() {
        mClearButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideClearButton() {
        mClearButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideAllElements() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mClearButton.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyListView() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyListView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingIndicator() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingView.setVisibility(View.GONE);
    }

    private class SearchListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String input = editable.toString();
            mPresenter.onInput(input);
        }
    }
}
