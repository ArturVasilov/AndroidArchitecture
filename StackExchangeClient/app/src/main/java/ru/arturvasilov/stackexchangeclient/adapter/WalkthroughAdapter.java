package ru.arturvasilov.stackexchangeclient.adapter;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.fragment.WalkthroughItemFragment;
import ru.arturvasilov.stackexchangeclient.presenter.WalkthroughPresenter;

/**
 * @author Artur Vasilov
 */
public class WalkthroughAdapter extends FragmentPagerAdapter {

    private static final WalkthroughPage[] PAGES = new WalkthroughPage[]
            {WalkthroughPage.PAGE_1, WalkthroughPage.PAGE_2, WalkthroughPage.PAGE_3};

    public WalkthroughAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return WalkthroughItemFragment.create(PAGES[position]);
    }

    @Override
    public int getCount() {
        return WalkthroughPresenter.PAGE_COUNT;
    }

    public enum WalkthroughPage {

        PAGE_1(R.drawable.page_1_background, R.drawable.page_1_logo, R.string.page_1_text),
        PAGE_2(R.drawable.page_2_background, R.drawable.so_logo, R.string.page_2_text),
        PAGE_3(R.drawable.page_3_background, R.drawable.page_3_logo, R.string.page_3_text);

        private final int mBackgroundDrawableResId;
        private final int mImageResId;
        private final int mTextResId;

        WalkthroughPage(@DrawableRes int backgroundDrawableResId, @DrawableRes int imageResId,
                        @StringRes int textResId) {
            mBackgroundDrawableResId = backgroundDrawableResId;
            mImageResId = imageResId;
            mTextResId = textResId;
        }

        @ColorRes
        public int getBackgroundDrawableResId() {
            return mBackgroundDrawableResId;
        }

        @DrawableRes
        public int getImageResId() {
            return mImageResId;
        }

        @StringRes
        public int getTextResId() {
            return mTextResId;
        }
    }
}
