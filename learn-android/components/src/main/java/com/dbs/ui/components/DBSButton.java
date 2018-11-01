package com.dbs.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.dbs.components.R;
import com.dbs.ui.styleable.StyleableComponent;
import com.dbs.ui.utils.ButtonBuilder;

public class DBSButton extends AppCompatButton implements StyleableComponent {
    private ButtonBuilder buttonBuilder = new ButtonBuilder(this);

    public DBSButton(Context context) {
        this(context, null);
    }

    public DBSButton(Context context, AttributeSet attrs) {
        this(context, attrs, NO_ID);
    }

    public DBSButton(Context context, AttributeSet attrs, int defStyleAttr) {
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