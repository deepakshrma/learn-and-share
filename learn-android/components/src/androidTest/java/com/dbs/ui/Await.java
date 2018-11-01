package com.dbs.ui;

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
            } catch (Exception ignored) {}
        }

        throw new Exception("Await: Condition not met exception");
    }

    public interface Condition {
        boolean isValid();
    }
}
