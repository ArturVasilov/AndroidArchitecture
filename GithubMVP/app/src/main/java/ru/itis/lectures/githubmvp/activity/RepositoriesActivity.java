package ru.itis.lectures.githubmvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.content.Repository;
import ru.itis.lectures.githubmvp.fragment.dialog.LoadingDialog;
import ru.itis.lectures.githubmvp.presenter.RepositoriesPresenter;
import ru.itis.lectures.githubmvp.repository.RepositoriesRepository;
import ru.itis.lectures.githubmvp.repository.impl.RepositoriesRepositoryImpl;
import ru.itis.lectures.githubmvp.view.RepositoriesView;
import ru.itis.lectures.githubmvp.widget.BaseAdapter;
import ru.itis.lectures.githubmvp.widget.DividerItemDecoration;
import ru.itis.lectures.githubmvp.widget.EmptyRecyclerView;
import ru.itis.lectures.githubmvp.widget.RepositoriesAdapter;

import static ru.itis.lectures.githubmvp.utils.Views.findById;

/**
 * @author Artur Vasilov
 */
public class RepositoriesActivity extends AppCompatActivity implements RepositoriesView,
        BaseAdapter.OnItemClickListener<Repository> {

    private LoadingDialog mProgressDialog;
    private RepositoriesAdapter mAdapter;

    private RepositoriesPresenter mPresenter;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, RepositoriesActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        setSupportActionBar(findById(this, R.id.toolbar));

        mProgressDialog = LoadingDialog.create(R.string.repositories_progress);

        EmptyRecyclerView recyclerView = findById(this, R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setEmptyView(findById(this, R.id.empty));

        mAdapter = new RepositoriesAdapter(new ArrayList<>());
        mAdapter.attachToRecyclerView(recyclerView);

        RepositoriesRepository repository = new RepositoriesRepositoryImpl();
        mPresenter = new RepositoriesPresenter(this, getLoaderManager(), this, repository);
        mPresenter.dispatchCreated();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onPause() {
        mAdapter.setOnItemClickListener(null);
        super.onPause();
    }

    @Override
    public void onItemClick(@NonNull Repository item) {
        mPresenter.onItemClick(item);
    }

    @Override
    public void showRepositories(@NonNull List<Repository> repositories) {
        mAdapter.setNewValues(repositories);
    }

    @Override
    public void showCommits(@NonNull Repository repository) {
        CommitsActivity.start(this, repository);
    }

    @Override
    public void showLoading() {
        mProgressDialog.show(getFragmentManager());
    }

    @Override
    public void hideLoading() {
        mProgressDialog.cancel();
    }

    @Override
    public void showError() {
        mAdapter.clear();
    }
}
