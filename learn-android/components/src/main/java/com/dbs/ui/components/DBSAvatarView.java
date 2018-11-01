package com.dbs.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.dbs.components.R;
import com.dbs.ui.utils.DBSAvatarViewHelper;
import com.dbs.ui.utils.LayoutUtils;

import static com.dbs.ui.utils.ViewUtils.dpToPixels;

/**
 * Act like a normal imageview
 * <p>
 * But with extension to show initial characters with gradient background
 * <p>
 * Im not a fan of creating a circular imageview . It can be obtained by transform the bitmap into expected shape.
 */
public class DBSAvatarView extends AppCompatImageView {


    private static final float DEFAULT_LETTER_VIEW_RATIO = 0.4f;

    /**
     * the initial characters paint
     */
    private TextPaint mInitialPaint;

    /**
     * The boundary for the view
     */

    private RectF mRect = new RectF();

    /**
     * retain the builder object
     */
    private LayoutUtils.GradientBuilder mInitialDrawableBuilder = new LayoutUtils.GradientBuilder();

    /**
     * The initials characters
     */
    private char[] mInitials = new char[0];


    /**
     * Refer to https://developer.android.com/reference/android/graphics/drawable/GradientDrawable.html#attr_android:shape
     */
    private int mGradientShape = GradientDrawable.OVAL;

    /**
     * Refer to https://developer.android.com/reference/android/graphics/drawable/GradientDrawable.html#setCornerRadius(float)
     */
    private int mCornerRadiusForGradient;

    /**
     * value should be from 0..1
     */
    private float mTextSizeRatio;

    public DBSAvatarView(Context context) {
        super(context);
    }

    public DBSAvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DBSAvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(
                attrs,
                R.styleable.DBSAvatarView,
                0, 0);

        mGradientShape = getSupportedShape(a.getInt(R.styleable.DBSAvatarView_dbs_avatarShape, GradientDrawable.OVAL));
        mCornerRadiusForGradient = a.getDimensionPixelOffset(R.styleable.DBSAvatarView_dbs_cornerRadius, dpToPixels(context, 4));
        mTextSizeRatio = a.getFloat(R.styleable.DBSAvatarView_dbs_avatarTextSizeRatio, DEFAULT_LETTER_VIEW_RATIO);
        a.recycle();
        mInitialPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mInitialPaint.setTextAlign(Paint.Align.CENTER);
        mInitialPaint.setColor(Color.WHITE);
        mInitialPaint.setTypeface(ResourcesCompat.getFont(context, R.font.roboto_regular));
    }


    /**
     * Only support Circle and Rectangle, all unknown value will be fallback to Oval
     */
    private int getSupportedShape(int inputShape) {
        return inputShape == GradientDrawable.RECTANGLE ? GradientDrawable.RECTANGLE : GradientDrawable.OVAL;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // make it square
        mRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mInitialPaint.setTextSize(calculateTextSize());
    }


    private float calculateTextSize() {
        if (mTextSizeRatio < 0 || mTextSizeRatio > 1) {
            mTextSizeRatio = DEFAULT_LETTER_VIEW_RATIO;
        }
        return mRect.height() * mTextSizeRatio;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mInitials.length != 0) {
            drawInitials(canvas);
        }
    }

    private void drawInitials(Canvas canvas) {
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((mInitialPaint.descent() + mInitialPaint.ascent()) / 2));
        canvas.drawText(String.valueOf(mInitials), xPos, yPos, mInitialPaint);
    }


    /**
     * Set the name to display the initials
     * For example, with "Jake Wharton" you'll see a gradient image with 2 white letter 'JW'
     *
     * @param name any string
     */
    public void setName(String name) {
        char[] initials = DBSAvatarViewHelper.getChars(name);
        if (initials.length != 0) {
            mInitialDrawableBuilder.withOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            mInitialDrawableBuilder.withPrimaryColor(DBSAvatarViewHelper.get(initials[0]));
            mInitialDrawableBuilder.withSecondaryColor((initials.length > 1 && initials[1] != 0) ? DBSAvatarViewHelper.get(initials[1]) : -1);
            mInitialDrawableBuilder.withShape(mGradientShape);
            mInitialDrawableBuilder.withRoundedCorner(mCornerRadiusForGradient);
            GradientDrawable gradientDrawable = mInitialDrawableBuilder.build();
            setImageDrawable(gradientDrawable);
        } else {
            invalidate();
        }
        mInitials = initials;

    }

    /**
     * Get initials only meant for testing purpose
     *
     * @return
     */
    public char[] getInitials() {
        return mInitials;
    }

    private void resetInitials() {
        // check the length so that allocation wont take place frequently.
        if (mInitials == null || mInitials.length > 0) {
            mInitials = new char[0];
        }
    }

    @Override
    public void setImageResource(int resId) {
        resetInitials();
        super.setImageResource(resId);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        resetInitials();
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        resetInitials();
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        resetInitials();
        super.setImageURI(uri);
    }

}
