package com.dbs.ui.components.testRule;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.annotation.LayoutRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbs.components.test.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ViewTestRule<T extends View> extends ActivityTestRule<EmptyActivity> {

    private final Instrumentation instrumentation;
    private final ViewCreator<T> viewCreator;

    private T view;

    public ViewTestRule(@LayoutRes int layoutId) {
        this(new InflateFromXmlViewCreator<T>(layoutId));
    }

    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public ViewTestRule(ViewCreator<T> viewCreator) {
        this(InstrumentationRegistry.getInstrumentation(), viewCreator);
    }

    protected ViewTestRule(Instrumentation instrumentation, ViewCreator<T> viewCreator) {
        super(EmptyActivity.class);
        this.instrumentation = instrumentation;
        this.viewCreator = viewCreator;
    }

    public static Matcher<View> underTest() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                Object tag = item.getTag(R.id.espresso_support__view_test_rule);
                return tag != null && ((Boolean) tag);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is View under test, managed by this " + ViewTestRule.class.getSimpleName());
            }
        };
    }

    public void runOnMainSynchronously(final Runner<T> runner) {
        instrumentation.runOnMainSync(() -> runner.run(view));
    }

    public T getView() {
        return view;
    }

    public void addView(View view) {
        instrumentation.runOnMainSync(() -> {
            ViewGroup viewGroup = (ViewGroup) ViewTestRule.this.view;
            viewGroup.addView(view);
        });
    }

    public void updateView(T view) {
        instrumentation.runOnMainSync(() -> {
            ViewGroup viewGroup = (ViewGroup) ViewTestRule.this.view;
            viewGroup.removeAllViews();
            viewGroup.addView(view);
            this.view = view;
        });
    }

    public void updateActivityToNewLayout(@LayoutRes int layoutRes) {
        View inflatedView = LayoutInflater.from(getActivity()).inflate(layoutRes, null);
        updateView((T) inflatedView);
    }

    public void updateActivityToNewView(View view) {
        updateView((T) view);
    }

    public void waitForIdleSync() {
        instrumentation.waitForIdleSync();
    }

    private void createView(final Activity activity) {
        instrumentation.runOnMainSync(() -> {
            view = viewCreator.createView(activity, (ViewGroup) activity.findViewById(android.R.id.content));
            view.setTag(R.id.espresso_support__view_test_rule, true);
        });
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        final EmptyActivity activity = getActivity();
        createView(activity);
        runOnMainSynchronously((v) -> activity.setContentView(view, view.getLayoutParams()));
    }

    public interface Runner<T> {

        void run(T view);
    }
}
