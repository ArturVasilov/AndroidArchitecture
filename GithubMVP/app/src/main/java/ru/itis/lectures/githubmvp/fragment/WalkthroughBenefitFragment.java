package ru.itis.lectures.githubmvp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.itis.lectures.githubmvp.R;
import ru.itis.lectures.githubmvp.content.Benefit;

import static ru.itis.lectures.githubmvp.utils.Views.findById;

/**
 * @author Artur Vasilov
 */
public class WalkthroughBenefitFragment extends Fragment {

    private static final String BENEFIT_KEY = "benefit";

    private Benefit mBenefit;

    @NonNull
    public static WalkthroughBenefitFragment create(@NonNull Benefit benefit) {
        Bundle bundle = new Bundle();
        bundle.putString(BENEFIT_KEY, benefit.name());
        WalkthroughBenefitFragment fragment = new WalkthroughBenefitFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String benefit = getArguments().getString(BENEFIT_KEY, Benefit.WORK_TOGETHER.name());
        mBenefit = Benefit.valueOf(benefit);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_benefit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView benefitIcon = findById(view, R.id.benefitIcon);
        TextView benefitText = findById(view, R.id.benefitText);

        benefitIcon.setImageResource(mBenefit.getDrawableId());
        benefitText.setText(mBenefit.getTextId());
    }
}
