package com.dbs.ui.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dbs.components.R;

import static com.dbs.ui.utils.Utils.hasSameDomainAs;

public class DBSWebView extends WebView {
    private String webUrl = "https://www.dbs.com";
    private String domainName = "dbs";
    private WebViewClient onLinkClickedListener;
    private WebViewClient onFinishedListener;

    public DBSWebView(Context context) {
        super(context);
    }

    public DBSWebView(Context context, AttributeSet attrs) {
        this(context, attrs, NO_ID);
    }

    public DBSWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    public void load() {
        if (hasSameDomainAs(webUrl, domainName)) {
            setWebViewClient(onFinishedListener);
            loadUrl(webUrl);
        }
    }

    public void load(String webUrl) {
        this.webUrl = webUrl;
        load();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(AttributeSet attrs) {
        getSettings().setJavaScriptEnabled(true);
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.DBSWebView, 0, 0);
        if (attributes.hasValue(R.styleable.DBSWebView_dbs_webUrl))
            webUrl = attributes.getString(R.styleable.DBSWebView_dbs_webUrl);
        if (attributes.hasValue(R.styleable.DBSWebView_dbs_webDomainName))
            domainName = attributes.getString(R.styleable.DBSWebView_dbs_webDomainName);
        attributes.recycle();
        onLinkClickedListener = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (hasSameDomainAs(url, domainName)) {
                    load(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        };
        onFinishedListener = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                setWebViewClient(onLinkClickedListener);
            }
        };
    }
}
