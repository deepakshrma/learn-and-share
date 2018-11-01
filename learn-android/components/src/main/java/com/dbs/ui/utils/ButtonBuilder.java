package com.dbs.ui.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.dbs.components.R;

import static com.dbs.ui.utils.ViewUtils.getRectShape;
import static com.dbs.ui.utils.ViewUtils.isTransparent;

/**
 * Created by deepak on 5/10/18.
 */

public class ButtonBuilder {
    AppCompatButton button;
    Context context;
    private StylableProps stylableProps;

    public ButtonBuilder(AppCompatButton button) {
        this.button = button;
        this.context = button.getContext();
    }

    public void init(AttributeSet attrs) {
        stylableProps = new StylableProps();
        getDefaultProps();
        updateProps(attrs);
        updateGradients();
    }

    public void applyStyle(TypedArray attributes) {
        int textColor = attributes.getColor(R.styleable.DBSButton_android_textColor, stylableProps.textColor);
        button.setTextColor(textColor);
        stylableProps.bgColor = attributes.getColor(R.styleable.DBSButton_android_background, stylableProps.bgColor);
        stylableProps.bgColorPressed = attributes.getColor(R.styleable.DBSButton_dbs_pressedColor, stylableProps.bgColorPressed);
        stylableProps.bgColorDisabled = attributes.getColor(R.styleable.DBSButton_dbs_disabledColor, stylableProps.bgColorDisabled);
        if (attributes.hasValue(R.styleable.DBSButton_dbs_cornerRadius)) {
            stylableProps.roundedCornerPx = attributes.getDimensionPixelSize(R.styleable.DBSButton_dbs_cornerRadius, stylableProps.roundedCornerPx);
        }
        if (attributes.hasValue(R.styleable.DBSButton_dbs_borderColor)) {
            stylableProps.borderColor = attributes.getColor(R.styleable.DBSButton_dbs_borderColor, stylableProps.borderColor);
        }
        if (attributes.hasValue(R.styleable.DBSButton_dbs_border)) {
            stylableProps.borderWidth = attributes.getDimensionPixelSize(R.styleable.DBSButton_dbs_border, stylableProps.borderWidth);
        }
        if (attributes.hasValue(R.styleable.DBSButton_dbs_icon)) {
            stylableProps.icon = attributes.getDrawable(R.styleable.DBSButton_dbs_icon);
        }
        if (attributes.hasValue(R.styleable.DBSButton_dbs_iconSize)) {
            stylableProps.iconSize = attributes.getDimensionPixelSize(R.styleable.DBSButton_dbs_iconSize, stylableProps.iconSize);
        }
        updateGradients();
    }

    private void updateGradients() {
        GradientDrawable enabled = getRectShape(stylableProps.bgColor, stylableProps.roundedCornerPx);
        GradientDrawable pressed = getRectShape(isTransparent(stylableProps.bgColor) ? Color.TRANSPARENT : stylableProps.bgColorPressed, stylableProps.roundedCornerPx);
        GradientDrawable disabled = getRectShape(isTransparent(stylableProps.bgColor) ? Color.TRANSPARENT : stylableProps.bgColorDisabled, stylableProps.roundedCornerPx);
        if (stylableProps.hasBorder()) {
            enabled.setStroke(stylableProps.borderWidth, stylableProps.borderColor);
            pressed.setStroke(stylableProps.borderWidth, stylableProps.borderColor);
            disabled.setStroke(stylableProps.borderWidth, stylableProps.borderColor);
        }
        StateListDrawable states = getStateListDrawable(enabled, pressed, disabled);
        button.setBackgroundDrawable(states);
        if (stylableProps.icon != null && stylableProps.icon instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) stylableProps.icon).getBitmap();
            stylableProps.icon = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, stylableProps.iconSize, stylableProps.iconSize, true));
        }
        button.setCompoundDrawablesWithIntrinsicBounds(stylableProps.icon, null, null, null);
    }

    private StateListDrawable getStateListDrawable(GradientDrawable enabled, GradientDrawable pressed, GradientDrawable disabled) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.stateNotNeeded}, enabled);
        states.addState(new int[]{-android.R.attr.state_pressed, android.R.attr.state_enabled}, enabled);
        states.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressed);
        states.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_enabled}, enabled);
        states.addState(new int[]{-android.R.attr.state_enabled}, disabled);
        return states;
    }

    private StylableProps getDefaultProps() {
        TypedArray defStyleAttrs = context.obtainStyledAttributes(R.style.DBSButton, R.styleable.DBSButton);
        stylableProps.bgColor = defStyleAttrs.getColor(R.styleable.DBSButton_android_background, Color.RED);
        stylableProps.textColor = defStyleAttrs.getColor(R.styleable.DBSButton_android_textColor, Color.WHITE);
        stylableProps.bgColorPressed = defStyleAttrs.getColor(R.styleable.DBSButton_dbs_pressedColor, stylableProps.bgColor);
        stylableProps.bgColorDisabled = defStyleAttrs.getColor(R.styleable.DBSButton_dbs_disabledColor, Color.GRAY);
        stylableProps.iconSize = defStyleAttrs.getDimensionPixelSize(R.styleable.DBSButton_dbs_iconSize, stylableProps.iconSize);
        defStyleAttrs.recycle();
        return stylableProps;
    }

    private void updateProps(AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DBSButton, 0, 0);
        applyStyle(attributes);
        attributes.recycle();
    }

    private class StylableProps {
        int textColor;
        int bgColor;
        int bgColorPressed;
        int bgColorDisabled;
        int roundedCornerPx = 100;
        int borderColor = -1;
        int borderWidth = 4;
        int iconSize = 24;
        Drawable icon;

        boolean hasBorder() {
            return borderColor != -1;
        }
    }
}
