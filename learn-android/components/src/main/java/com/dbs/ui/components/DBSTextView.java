package com.dbs.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;

import com.dbs.components.R;
import com.dbs.ui.utils.StringUtils;

public class DBSTextView extends AppCompatTextView {
    private static final int MIN_HTML_LENGTH = 10;
    private OnAnchorClickListener onAnchorClickListener;


    public DBSTextView(Context context) {
        super(context);
    }

    public DBSTextView(Context context, AttributeSet attrs) {
        this(context, attrs, NO_ID);
    }

    public DBSTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.DBSTextView, 0, 0);
        if (attributes.hasValue(R.styleable.DBSTextView_dbs_textHtmlText)) {
            String htmlString = attributes.getString(R.styleable.DBSTextView_dbs_textHtmlText);
            setTextViewHTML(htmlString);
        }
        // Added rounded corner
        if (attributes.hasValue(R.styleable.DBSTextView_dbs_cornerRadius)) {
            int rnCorner = attributes.getDimensionPixelSize(R.styleable.DBSTextView_dbs_cornerRadius, 0);
            int bgColor = attributes.getColor(R.styleable.DBSTextView_android_background, Color.TRANSPARENT);
            GradientDrawable shape = new GradientDrawable();
            shape.setCornerRadius(rnCorner);
            shape.setColor(bgColor);
            setBackgroundDrawable(shape);
        }
        attributes.recycle();
    }

    public void setTextViewHTML(String html) {
        if (StringUtils.isEmpty(html) || html.length() < MIN_HTML_LENGTH) {
            setText(html);
            return;
        }
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }
        setText(strBuilder);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setOnAnchorClickListener(OnAnchorClickListener listener) {
        this.onAnchorClickListener = listener;
    }

    private void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                if (onAnchorClickListener != null) {
                    onAnchorClickListener.onClick(span);
                }
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    public interface OnAnchorClickListener {
        void onClick(URLSpan span);
    }
}
