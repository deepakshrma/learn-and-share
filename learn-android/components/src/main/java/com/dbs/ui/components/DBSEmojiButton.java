package com.dbs.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.text.emoji.widget.EmojiAppCompatButton;
import android.util.AttributeSet;

import com.dbs.components.R;
import com.dbs.ui.styleable.StyleableComponent;
import com.dbs.ui.utils.ButtonBuilder;

public class DBSEmojiButton extends EmojiAppCompatButton implements StyleableComponent {
    private ButtonBuilder buttonBuilder = new ButtonBuilder(this);

    public DBSEmojiButton(Context context) {
        this(context, null);
    }

    public DBSEmojiButton(Context context, AttributeSet attrs) {
        this(context, attrs, NO_ID);
    }

    public DBSEmojiButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, NO_ID == defStyleAttr ? R.attr.dbs_themeButtonStyle : defStyleAttr);
        if (defStyleAttr == NO_ID) {
            buttonBuilder.init(attrs);
        }
    }

    @Override
    public void applyStyle(int style) {
        TypedArray attributes = getContext().obtainStyledAttributes(style, R.styleable.DBSButton);
        buttonBuilder.applyStyle(attributes);
        attributes.recycle();
    }
}