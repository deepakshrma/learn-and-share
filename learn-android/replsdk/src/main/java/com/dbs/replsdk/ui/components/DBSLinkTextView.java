package com.dbs.replsdk.ui.components;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.dbs.ui.components.DBSTextView;
import com.dbs.ui.styleable.StyleableComponent;

/**
 * Created by deepak on 8/10/18.
 */

public class DBSLinkTextView extends DBSTextView implements StyleableComponent {
    public DBSLinkTextView(Context context) {
        super(context);
    }

    public DBSLinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSLinkTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLink(String text, OnClickListener onClickListener) {
        setText(text);
        setOnClickListener(onClickListener);
    }

    public void setLinkUnderlined(String text, OnClickListener onClickListener) {
        setLink(text, onClickListener);
        setPaintFlags(getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void applyStyle(int style) {
        setTextAppearance(getContext(), style);
    }
}