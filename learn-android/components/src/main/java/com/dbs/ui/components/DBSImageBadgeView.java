package com.dbs.ui.components;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.dbs.components.R;

/**
 * Image View with a badge like notification count
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSImageBadgeView extends AppCompatImageView {

    private static final int MAX_BADGE_VALUE_ALLOWED = 99;
    private static final float DEFAULT_BADGE_VIEW_RATIO = 0.25f;
    private Paint textPaint;
    private Paint backgroundPaint;
    private String badgeValue;
    private boolean shouldShowBadge;
    private int maxBadgeValue;
    private int minBadgeSize;
    private int badgeTextPadding;
    private float defaultTextSize;

    public DBSImageBadgeView(Context context) {
        super(context);
        init(null);
    }

    public DBSImageBadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DBSImageBadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DBSImageBadgeView);
        int badgeCount = typedArray.getInt(R.styleable.DBSImageBadgeView_dbs_badge, 0);
        int maxAllowedBadgeValue = typedArray.getInt(R.styleable.DBSImageBadgeView_dbs_badgeMax, MAX_BADGE_VALUE_ALLOWED);
        setMaxBadgeValue(maxAllowedBadgeValue);
        shouldShowBadge = badgeCount > 0;
        setBadgeValue(badgeCount);
        typedArray.recycle();

        textPaint = new Paint();
        Resources resources = getResources();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(resources.getColor(android.R.color.black));

        minBadgeSize = resources.getDimensionPixelSize(R.dimen.min_badge_size);
        badgeTextPadding = resources.getDimensionPixelSize(R.dimen.xx_small_padding);
        defaultTextSize = resources.getDimensionPixelSize(R.dimen.badge_text_size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (shouldShowBadge) {
            int badgeSize = (int) textPaint.measureText(badgeValue) + badgeTextPadding;
            if (badgeSize < minBadgeSize) {
                badgeSize = minBadgeSize;
            }
            int radius = badgeSize / 2;
            int x = canvas.getWidth() - radius;

            canvas.drawCircle(x, radius, radius, backgroundPaint);

            canvas.drawText(badgeValue, x, radius - (textPaint.ascent() + textPaint.descent()) / 2, textPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float availableTextSize = DEFAULT_BADGE_VIEW_RATIO * getMeasuredHeight();
        textPaint.setTextSize(defaultTextSize > availableTextSize ? availableTextSize : defaultTextSize);
    }

    /**
     * Sets badge value.
     * If the value is less than max allowed, it would be rounded to the max allowed value and displayed with +.
     * e.g. If the maximum allowed badge value is 99 and the value we are trying to set is 102
     * then actual badge value displayed is '99+'
     */
    public void setBadgeValue(int badgeValue) {
        shouldShowBadge = badgeValue > 0;
        if (badgeValue > maxBadgeValue) {
            this.badgeValue = String.valueOf(maxBadgeValue + "+");
        } else {
            this.badgeValue = String.valueOf(badgeValue);
        }
        invalidate();
    }

    /**
     * Sets max allowed badge value.
     */
    public void setMaxBadgeValue(int maxBadgeValue) {
        this.maxBadgeValue = maxBadgeValue;
    }
}