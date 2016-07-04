package ru.itis.lectures.githubmvp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.presenter.WalkthroughPresenter;
import ru.itis.lectures.githubmvp.view.WalkthroughView;
import ru.itis.lectures.githubmvp.widget.PageChangeViewPager;
import ru.itis.lectures.githubmvp.widget.WalkthroughAdapter;

import static ru.itis.lectures.githubmvp.utils.Views.findById;

/**
 * @author Artur Vasilov
 */
public class WalkthroughActivity extends AppCompatActivity implements View.OnClickListener,
        PageChangeViewPager.PagerStateListener, WalkthroughView {

    private PageChangeViewPager mPager;
    private Button mActionButton;

    private WalkthroughPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        mPager = findById(this, R.id.pager);
        mActionButton = findById(this, R.id.btn_walkthrough);

        mPresenter = new WalkthroughPresenter(this, this);
        mPresenter.dispatchCreated();

        mPager.setAdapter(new WalkthroughAdapter(getFragmentManager(), mPresenter.getBenefits()));

        mActionButton.setText(R.string.next_uppercase);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActionButton.setOnClickListener(this);
        mPager.setOnPageChangedListener(this);
    }

    @Override
    protected void onPause() {
        mActionButton.setOnClickListener(null);
        mPager.setOnPageChangedListener(null);
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_walkthrough) {
            mPresenter.onButtonClick();
        }
    }

    @Override
    public void onPageChanged(int selectedPage, boolean fromUser) {
        if (fromUser) {
            mPresenter.onBenefitSelected(selectedPage);
        }
    }

    @Override
    public void showBenefit(int index, boolean isLastBenefit) {
        mActionButton.setText(isLastBenefit ? R.string.finish_uppercase : R.string.next_uppercase);
        if (index == mPager.getCurrentItem()) {
            return;
        }
        mPager.smoothScrollNext(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    @Override
    public void finishWalkthrough() {
        startLogInActivity();
    }

    @Override
    public void startLogInActivity() {
        LogInActivity.start(this);
        finish();
    }

}
