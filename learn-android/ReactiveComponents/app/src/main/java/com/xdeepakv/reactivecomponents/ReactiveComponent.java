package com.xdeepakv.reactivecomponents;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class ReactiveComponent<P, S> extends RelativeLayout {
    P props;
    S state;
    View view;
    static Observable appSubs;

    public ReactiveComponent(Context context) {
        this(context, null, -1);
    }

    public ReactiveComponent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ReactiveComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (appSubs != null) {
            appSubs.subscribe(v -> {
                state = (S) new State(((Events.Message) v).getMessage());
                view = this.render(context, props, state);
            });
        }

    }

    abstract View render(Context context, P props, S state);

    public boolean updateProps(P props) {
        this.props = props;
        return true;
    }

    public boolean updateState(S state) {
        this.state = state;
        return true;
    }

    public static void register(ReduxApp application) {
        appSubs = application.bus().toObserverable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
