package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.model.content.Video;

/**
 * @author Artur Vasilov
 */
public final class Videos {

    private static final String YOUTUBE = "https://www.youtube.com/watch?v=";

    private Videos() {
    }

    public static void browseVideo(@NonNull Context context, @NonNull Video video) {
        String videoUrl = YOUTUBE + video.getKey();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        context.startActivity(intent);
    }

}
