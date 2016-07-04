package ru.itis.lectures.githubmvp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.fragment.dialog.LoadingDialog;
import ru.itis.lectures.githubmvp.presenter.LogInPresenter;
import ru.itis.lectures.githubmvp.view.LogInView;

import static ru.itis.lectures.githubmvp.utils.Views.findById;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener, LogInView {

    private EditText mLoginEdit;
    private EditText mPasswordEdit;
    private TextInputLayout mLoginInputLayout;
    private TextInputLayout mPasswordInputLayout;
    private Button mLogInButton;

    private LoadingDialog mProgressDialog;

    private LogInPresenter mPresenter;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, LogInActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findById(this, R.id.toolbar));

        mLoginEdit = findById(this, R.id.loginEdit);
        mPasswordEdit = findById(this, R.id.passwordEdit);
        mLogInButton = findById(this, R.id.logInButton);
        mLoginInputLayout = findById(this, R.id.loginInputLayout);
        mPasswordInputLayout = findById(this, R.id.passwordInputLayout);

        mProgressDialog = LoadingDialog.create(R.string.log_in_progress);

        mPresenter = new LogInPresenter(this, getLoaderManager(), this);
        mPresenter.checkIfAuthorized();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLogInButton.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        mLogInButton.setOnClickListener(null);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logInButton) {
            String login = mLoginEdit.getText().toString();
            String password = mPasswordEdit.getText().toString();
            mPresenter.tryLogIn(login, password);
        }
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
        showLoginError();
    }

    public void showLoginError() {
        mLoginInputLayout.setError(getString(R.string.error));
    }

    public void showPasswordError() {
        mPasswordInputLayout.setError(getString(R.string.error));
    }

    public void openRepositoriesScreen() {
        RepositoriesActivity.start(this);
        finish();
    }
}
