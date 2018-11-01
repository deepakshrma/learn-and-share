package com.dbs.ui.components.forms;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.dbs.ui.utils.TextWatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

abstract class DBSBaseTextInputLayout extends TextInputLayout {
    private Subscription textChangeSubscription;
    private List<FormValidateRule> rules = new ArrayList<>();
    private PublishSubject<String> textChangePublishSubject;
    private TextWatcher textWatcher;
    private long validateTimeout = 500;
    private boolean isRequired;

    public DBSBaseTextInputLayout(Context context) {
        super(context);
    }

    public DBSBaseTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DBSBaseTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Validates text entered in text input.
     *
     * @param rules   rules with which text is validated.
     * @param timeOut sets validate time out.
     */
    public void validateWith(List<FormValidateRule> rules, long timeOut) {
        this.rules = rules;
        this.validateTimeout = timeOut;
        initTextWatcher();
        setFocusChangeListener();
    }

    /**
     * Validates text entered in text input.
     *
     * @param rules rules with which text is validated.
     */
    public void validateWith(List<FormValidateRule> rules) {
        validateWith(rules, validateTimeout);
    }

    /**
     * Sets if the input is mandatory or not.
     */
    public void setRequired(boolean required) {
        this.isRequired = required;
    }

    /**
     * Returns if the input is valid or not.
     */
    public boolean isValidInput() {
        return getInputState().isValidInput;
    }

    /**
     * Sets error if not valid input.
     * Method gets called when the text changes.
     */
    public void handleInput() {
        State inputState = getInputState();
        if (inputState.isValidInput) {
            hideError();
        } else {
            setError(inputState.errorMessage);
        }
    }

    protected abstract TextInputEditText getTextInputEditTextView();

    protected abstract void hideError();

    private State getInputState() {
        TextInputEditText textInputEditTextView = getTextInputEditTextView();
        if (textInputEditTextView != null) {
            String input = textInputEditTextView.getText().toString();
            for (FormValidateRule rule : rules) {
                if (input.isEmpty() && !isRequired) {
                    return new State(true, null);
                }

                if (!rule.validate(input)) {
                    return new State(false, rule.errorMessage());
                }
            }
        }
        return new State(true, null);
    }

    private void initTextWatcher() {
        textChangePublishSubject = PublishSubject.create();
        textWatcher = new TextWatchers.AfterChangeTextWatcher() {
            @Override
            public void onTextChange(String currentText) {
                textChangePublishSubject.onNext(currentText);
            }
        };
    }

    private void setFocusChangeListener() {
        TextInputEditText textInputEditTextView = getTextInputEditTextView();
        if (textInputEditTextView != null) {
            textInputEditTextView.setOnFocusChangeListener((view, hasFocus) -> {
                if (hasFocus) {
                    subscribeToTextChange();
                } else {
                    unsubscribeTextChange();
                }
            });
        }
    }

    private void subscribeToTextChange() {
        setTextChangeListener();
        textChangeSubscription = textChangePublishSubject.throttleWithTimeout(validateTimeout, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> handleInput());
    }

    private void unsubscribeTextChange() {
        if (textChangeSubscription != null) {
            textChangeSubscription.unsubscribe();
        }
    }

    private void setTextChangeListener() {
        TextInputEditText textInputEditTextView = getTextInputEditTextView();
        if (textInputEditTextView != null) {
            textInputEditTextView.removeTextChangedListener(textWatcher);
            textInputEditTextView.addTextChangedListener(textWatcher);
        }
    }

    class State {
        boolean isValidInput;
        String errorMessage;

        State(boolean isValidInput, String errorMessage) {
            this.isValidInput = isValidInput;
            this.errorMessage = errorMessage;
        }
    }
}