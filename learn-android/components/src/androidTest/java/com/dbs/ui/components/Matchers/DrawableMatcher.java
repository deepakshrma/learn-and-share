package com.dbs.ui.components.Matchers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DrawableMatcher extends TypeSafeMatcher<View> {

    private final int expectedId;
    private int drawablePosition = 0;
    private String resourceName;
    static final int EMPTY = -1;
    static final int ANY = -2;

    DrawableMatcher(int expectedId) {
        super(View.class);
        this.expectedId = expectedId;
    }

    DrawableMatcher(int expectedId, int drawablePosition) {
        super(View.class);
        this.expectedId = expectedId;
        this.drawablePosition = drawablePosition;
    }

    @Override
    protected boolean matchesSafely(View target) {
        if (!(target instanceof ImageView) && !(target instanceof TextView)) {
            return false;
        }
        Drawable drawable = null;
        if (target instanceof ImageView) {
            ImageView imageView = (ImageView) target;
            if (expectedId == EMPTY) {
                return imageView.getDrawable() == null;
            }
            if (expectedId == ANY) {
                return imageView.getDrawable() != null;
            }
            drawable = imageView.getDrawable();
        } else {
            TextView textView = (TextView) target;
            drawable = textView.getCompoundDrawables()[drawablePosition];
        }
        Resources resources = target.getContext().getResources();
        Drawable expectedDrawable = resources.getDrawable(expectedId);
        resourceName = resources.getResourceEntryName(expectedId);

        if (expectedDrawable == null) {
            return false;
        }

        Bitmap bitmap = getBitmap(drawable);
        Bitmap otherBitmap = getBitmap(expectedDrawable);
        return bitmap.sameAs(otherBitmap);
    }

    private Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable from resource id: ");
        description.appendValue(expectedId);
        if (resourceName != null) {
            description.appendText("[");
            description.appendText(resourceName);
            description.appendText("]");
        }
    }
}
