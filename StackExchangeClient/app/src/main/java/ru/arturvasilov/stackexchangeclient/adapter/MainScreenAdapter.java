package ru.arturvasilov.stackexchangeclient.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import ru.arturvasilov.stackexchangeclient.fragment.QuestionsListFragment;

/**
 * @author Artur Vasilov
 */
public class MainScreenAdapter extends FragmentPagerAdapter {

    private final List<String> mTags;

    public MainScreenAdapter(FragmentManager fm, @NonNull List<String> tags) {
        super(fm);
        mTags = tags;
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionsListFragment.create(mTags.get(position));
    }

    @Override
    public int getCount() {
        return mTags.size();
    }
}
