package ru.arturvasilov.stackexchangeclient.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.Button;

import ru.arturvasilov.stackexchangeclient.app.Env;
import ru.arturvasilov.stackexchangeclient.app.analytics.Analytics;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventKeys;
import ru.arturvasilov.stackexchangeclient.app.analytics.EventTags;
import ru.arturvasilov.stackexchangeclient.model.content.Badge;

/**
 * @author Artur Vasilov
 */
public class BadgeButton extends Button {

    public BadgeButton(Context context) {
        super(context);
    }

    public BadgeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BadgeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBadge(@NonNull Badge badge) {
        setText(badge.getName());
        setOnClickListener(view -> {
            Analytics.buildEvent()
                    .putString(EventKeys.PROFILE_BADGE, badge.getName())
                    .log(EventTags.PROFILE_BADGE_CLICKED);
            Env.browseUrl(getContext(), badge.getLink());
        });
    }

}
