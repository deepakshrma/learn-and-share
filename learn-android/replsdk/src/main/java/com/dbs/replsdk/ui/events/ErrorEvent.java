package com.dbs.replsdk.ui.events;

public class ErrorEvent implements SubjectEvent {
    private final Throwable throwable;

    public ErrorEvent(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getErrorMessage() {
        return throwable.getMessage();
    }
}
