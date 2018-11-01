package com.dbs.replsdk;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

public class Await {

    @SuppressWarnings("squid:S2925")
    public static void await(Condition condition, int millis) throws Exception {
        int sleptTime = 0;
        int defaultSleepInterval = 30;
        while (sleptTime < millis) {
            Thread.sleep(defaultSleepInterval);
            try {
                if (condition.isValid()) {
                    return;
                }
                sleptTime += defaultSleepInterval;
            } catch (Exception ignored) {
            }
        }

        throw new Exception("Await: Condition not met exception");
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    public interface Condition {
        boolean isValid();
    }
}