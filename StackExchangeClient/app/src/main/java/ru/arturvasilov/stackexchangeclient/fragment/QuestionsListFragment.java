package ru.arturvasilov.stackexchangeclient.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.activity.ProfileActivity;
import ru.arturvasilov.stackexchangeclient.activity.QuestionActivity;
import ru.arturvasilov.stackexchangeclient.adapter.QuestionsListAdapter;
import ru.arturvasilov.stackexchangeclient.api.ApiConstants;
import ru.arturvasilov.stackexchangeclient.model.content.Question;
import ru.arturvasilov.stackexchangeclient.presenter.QuestionsListPresenter;
import ru.arturvasilov.stackexchangeclient.utils.TextUtils;
import ru.arturvasilov.stackexchangeclient.utils.Views;
import ru.arturvasilov.stackexchangeclient.view.QuestionsListView;

/**
 * @author Artur Vasilov
 */
public class QuestionsListFragment extends Fragment implements QuestionsListView,
        QuestionsListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG_KEY = "tag_key";

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;

    private QuestionsListAdapter mAdapter;
    private QuestionsListPresenter mPresenter;

    @NonNull
    public static QuestionsListFragment create(@NonNull String tag) {
        QuestionsListFragment fragment = new QuestionsListFragment();
        Bundle args = new Bundle();
        args.putString(TAG_KEY, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tag = getArguments().getString(TAG_KEY);
        tag = TextUtils.isEmpty(tag) ? ApiConstants.TAG_ALL : tag;
        mPresenter = new QuestionsListPresenter(getActivity(), getActivity().getLoaderManager(), this, tag);
        mAdapter = new QuestionsListAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fr_questions_list, container, false);
        mRecyclerView = Views.findById(mRefreshLayout, R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEmptyView = Views.findById(mRefreshLayout, R.id.empty);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.primary),
                ContextCompat.getColor(getActivity(), R.color.primary_dark),
                ContextCompat.getColor(getActivity(), R.color.accent));
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dy > 0) {
                    mPresenter.onScrolled(layoutManager.findLastVisibleItemPosition());
                }
            }
        });
        return mRefreshLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmptyView.setText(R.string.empty_questions);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.init();
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onItemClick(@NonNull Question question, @NonNull View view) {
        if (view.getId() == R.id.icon) {
            ProfileActivity.start(getActivity(), view, question.getOwner());
        } else {
            QuestionActivity.start(getActivity(), question);
        }
    }

    @Override
    public void showQuestions(@NonNull List<Question> questions) {
        mAdapter.changeDataSet(questions);
    }

    @Override
    public void addQuestions(@NonNull List<Question> questions) {
        mAdapter.addNewValues(questions);
    }

    @Override
    public void hideRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmptyListView() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyListView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }
}
