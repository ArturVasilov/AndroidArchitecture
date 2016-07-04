package ru.itis.lectures.githubmvp.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.itis.lectures.githubmvp.content.Benefit;
import ru.itis.lectures.githubmvp.content.Settings;
import ru.itis.lectures.githubmvp.view.WalkthroughView;

/**
 * @author Artur Vasilov
 */
public class WalkthroughPresenter {

    private static final int PAGES_COUNT = 3;

    private final Context mContext;
    private final WalkthroughView mView;

    private int mCurrentItem = 0;

    public WalkthroughPresenter(Context context, @NonNull WalkthroughView view) {
        mContext = context;
        mView = view;
    }

    public void dispatchCreated() {
        if (Settings.isWalkthroughPassed(mContext)) {
            mView.startLogInActivity();
        }
    }

    @NonNull
    public List<Benefit> getBenefits() {
        return new ArrayList<Benefit>() {
            {
                add(Benefit.WORK_TOGETHER);
                add(Benefit.CODE_HISTORY);
                add(Benefit.PUBLISH_SOURCE);
            }
        };
    }

    public int getPageCount() {
        return PAGES_COUNT;
    }

    public void onButtonClick() {
        if (isLastBenefit()) {
            Settings.saveWalkthroughPassed(mContext);
            mView.finishWalkthrough();
        } else {
            mCurrentItem++;
            mView.showBenefit(mCurrentItem, isLastBenefit());
        }
    }

    public void onBenefitSelected(int index) {
        mCurrentItem = index;
        mView.showBenefit(mCurrentItem, isLastBenefit());
    }

    private boolean isLastBenefit() {
        return mCurrentItem == getPageCount() - 1;
    }
}

