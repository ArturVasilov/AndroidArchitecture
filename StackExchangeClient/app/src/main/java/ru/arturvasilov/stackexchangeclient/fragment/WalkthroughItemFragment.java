package ru.arturvasilov.stackexchangeclient.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.arturvasilov.stackexchangeclient.R;
import ru.arturvasilov.stackexchangeclient.adapter.WalkthroughAdapter;
import ru.arturvasilov.stackexchangeclient.utils.Views;

/**
 * @author Artur Vasilov
 */
public class WalkthroughItemFragment extends Fragment {

    private static final String PAGE_KEY = "page_key";

    @NonNull
    public static WalkthroughItemFragment create(@NonNull WalkthroughAdapter.WalkthroughPage page) {
        WalkthroughItemFragment fragment = new WalkthroughItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(PAGE_KEY, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_walkthrough_item, container, false);

        WalkthroughAdapter.WalkthroughPage page = (WalkthroughAdapter.WalkthroughPage)
                getArguments().getSerializable(PAGE_KEY);
        page = page == null ? WalkthroughAdapter.WalkthroughPage.PAGE_1 : page;

        view.setBackground(ContextCompat.getDrawable(getActivity(), page.getBackgroundDrawableResId()));
        Views.<TextView>findById(view, R.id.text).setText(page.getTextResId());
        ImageView image = Views.findById(view, R.id.image);
        image.setImageResource(page.getImageResId());
        image.setContentDescription(getString(page.getTextResId()));

        return view;
    }
}
