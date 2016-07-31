package ru.arturvasilov.stackexchangeclient.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * @author Artur Vasilov
 */
public final class PicassoUtils {

    private static final int MEMORY_CACHE_SIZE = 1024 * 1024 * 10;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 50;

    private PicassoUtils() {
    }

    public static void setup(@NonNull Context context) {
        Picasso picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(context, DISK_CACHE_SIZE))
                .memoryCache(new LruCache(MEMORY_CACHE_SIZE))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    @NonNull
    public static Transformation circleTransform() {
        return new CircleTransform();
    }

    private static class CircleTransform implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap.Config config = source.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, config);

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
