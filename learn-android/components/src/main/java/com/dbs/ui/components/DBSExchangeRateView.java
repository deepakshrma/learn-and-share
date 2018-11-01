package com.dbs.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dbs.components.R;
import com.dbs.ui.styleable.StyleableComponent;

import static com.dbs.ui.utils.ViewUtils.rotate180;

/**
 * @author DBS Bank, AppStudio Team
 * @version 1.0.0
 * @since 2018-05-01
 */
public class DBSExchangeRateView extends FrameLayout implements StyleableComponent {
    private TextView fromAmtView;
    private TextView toAmtView;
    private ImageView exchangeView;
    private OnClickListener onClickListener;

    public DBSExchangeRateView(Context context) {
        this(context, null, NO_ID);
    }

    public DBSExchangeRateView(Context context, AttributeSet attrs) {
        this(context, attrs, NO_ID);
    }

    public DBSExchangeRateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_dbs_exchange, this);
        init(view, attrs);
    }

    /**
     * Set the values for DBSExchangeRate
     *
     * @param fromAmt
     * @param toAmt
     * @param exchangeIconId: Id of Drawable
     */
    public void setValue(String fromAmt, String toAmt, @DrawableRes int exchangeIconId) {
        Drawable exchangeIcon = getContext().getResources().getDrawable(exchangeIconId);
        setValue(fromAmt, toAmt, exchangeIcon);
    }

    /**
     * Set the values for DBSExchangeRate
     *
     * @param fromAmt
     * @param toAmt
     * @param exchangeIcon
     */
    public void setValue(String fromAmt, String toAmt, Drawable exchangeIcon) {
        fromAmtView.setText(fromAmt);
        toAmtView.setText(toAmt);
        exchangeView.setImageDrawable(exchangeIcon);
    }

    /**
     * Set the values for DBSExchangeRate
     * @return String value
     */
    public String getValue() {
        return String.format("%s = %s",fromAmtView.getText(), toAmtView.getText());
    }

    /**
     * Set the values for DBSExchangeRate
     *
     * @param fromAmt
     * @param toAmt
     */
    public void setValue(String fromAmt, String toAmt) {
        fromAmtView.setText(fromAmt);
        toAmtView.setText(toAmt);
    }

    /**
     * Set on click listener
     *
     * @param listener
     */
    public void setOnExchangeListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    /**
     * Change the style of DBSExchangeRateView Programmatically
     * Ex.
     * <code>
     * <style name="DBSExchangeRateCustom">
     * <item name="android:textColor">?themeTertiaryTextColor</item>
     * <item name="android:backgroundTint">?themeSecondaryColor</item>
     * </style>
     * </code>
     *
     * @param style
     */

    @Override
    public void applyStyle(@StyleRes int style) {
        TypedArray attributes = getContext().obtainStyledAttributes(style, R.styleable.DBSExchangeRateView);
        applyStyle(attributes);
        attributes.recycle();
    }

    private void init(View view, AttributeSet attrs) {
        fromAmtView = view.findViewById(R.id.amt_from);
        toAmtView = view.findViewById(R.id.amt_to);
        exchangeView = view.findViewById(R.id.ic_exchange);
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.DBSExchangeRateView);
        Drawable exchangeIcon = attributes.getDrawable(R.styleable.DBSExchangeRateView_android_src);
        final String fromAmt = attributes.getString(R.styleable.DBSExchangeRateView_dbs_exchangeFromAmt);
        final String toAmt = attributes.getString(R.styleable.DBSExchangeRateView_dbs_exchangeToAmt);
        setValue(fromAmt, toAmt, exchangeIcon);
        view.setOnClickListener(v -> {
            exchangeView.startAnimation(rotate180(getContext(), true));
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        });
        applyStyle(attributes);
        attributes.recycle();
    }

    private void applyStyle(TypedArray attributes) {
        if (attributes.hasValue(R.styleable.DBSExchangeRateView_android_textColor)) {
            int titleColor = attributes.getColor(R.styleable.DBSExchangeRateView_android_textColor, Color.BLACK);
            fromAmtView.setTextColor(titleColor);
            toAmtView.setTextColor(titleColor);
        }
        if (attributes.hasValue(R.styleable.DBSExchangeRateView_android_backgroundTint)) {
            int backgroundTint = attributes.getColor(R.styleable.DBSExchangeRateView_android_backgroundTint, Color.RED);
            exchangeView.setColorFilter(backgroundTint, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
