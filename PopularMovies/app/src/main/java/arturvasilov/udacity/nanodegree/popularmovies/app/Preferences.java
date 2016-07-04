package arturvasilov.udacity.nanodegree.popularmovies.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author Artur Vasilov
 */
public class Preferences {

    public static final String SETTINGS_NAME = "movies_prefs";

    private static final String TYPE_KEY = "movies_type_key";

    private static final String POPULAR_MOVIE_TYPE = "popular";
    private static final String TOP_RATED_MOVIE_TYPE = "top_rated";

    public static boolean isPopularMovies(@NonNull Context context) {
        SharedPreferences prefs = getPrefs(context);
        if (!prefs.contains(TYPE_KEY)) {
            prefs.edit().putString(TYPE_KEY, POPULAR_MOVIE_TYPE).apply();
            return true;
        }
        return TextUtils.equals(POPULAR_MOVIE_TYPE, prefs.getString(TYPE_KEY, ""));
    }

    @NonNull
    public static SharedPreferences getPrefs(@NonNull Context context) {
        return context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

}
