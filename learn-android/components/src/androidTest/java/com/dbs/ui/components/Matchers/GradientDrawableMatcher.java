package com.dbs.ui.components.Matchers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class GradientDrawableMatcher extends TypeSafeMatcher<View> {

    GradientDrawable mDrawable;

    public GradientDrawableMatcher(GradientDrawable drawable) {
        super(View.class);
        mDrawable = drawable;

    }

    @Override
    protected boolean matchesSafely(View target) {

        if (!(target instanceof ImageView)) {
            return false;
        }
        ImageView imageView = (ImageView) target;

        Drawable drawable = imageView.getDrawable();
        if (!(drawable instanceof GradientDrawable))
            return false;
        else {
//                    Arrays.equals(((LayoutUtils.ColorGradientDrawable) drawable).getColors(), mDrawable.getColors()) &&
//                            ((GradientDrawable) drawable).getOrientation() == mDrawable.getOrientation()
//                            && ((GradientDrawable) drawable).getShape() == mDrawable.getShape();


            Bitmap bitmap = getBitmap(imageView.getDrawable(), imageView.getWidth(), imageView.getHeight());
            Bitmap otherBitmap = getBitmap(mDrawable, imageView.getWidth(), imageView.getHeight());
            return bitmap.sameAs(otherBitmap);
        }


    }

    private Bitmap getBitmap(Drawable drawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    @Override
    public void describeTo(Description description) {
        description.appendText("Drawable matching...");
    }
}
