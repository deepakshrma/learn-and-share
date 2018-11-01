package com.dbs.replsdk.ui.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ImageView;

import com.dbs.replsdk.R;

import java.lang.reflect.Field;

import static android.view.View.INVISIBLE;
import static android.view.View.NO_ID;
import static android.view.View.VISIBLE;

/**
 * Created by deepak on 9/10/18.
 */

public final class ViewUtils {
    private ViewUtils() {
        // Do nothing
    }

    @DrawableRes
    public static int resourceByName(String drawableName) {
        int drawableId = NO_ID;
        try {
            Class res = R.drawable.class;
            Field field = res.getField(drawableName);
            drawableId = field.getInt(null);
        } catch (Exception e) {
            Log.e("ViewUtils", "Failure to get drawable id.", e);
        }
        return drawableId;
    }

    public static void switchImageWithAnimation(ImageView imageView, Drawable nextIconId) {
        final float widthOfIcon = imageView.getWidth();
        imageView.animate().translationX(widthOfIcon - 100)
                .setDuration(200).withEndAction(() -> {
            imageView.setImageDrawable(nextIconId);
            imageView.setVisibility(INVISIBLE);
            imageView.animate().translationXBy(-100).translationX(0).withEndAction(() -> {
                imageView.setVisibility(VISIBLE);
            });
        });
    }
}
