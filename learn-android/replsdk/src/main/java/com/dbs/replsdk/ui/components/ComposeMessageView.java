package com.dbs.replsdk.ui.components;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.transition.TransitionManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dbs.replsdk.R;

public class ComposeMessageView extends ConstraintLayout
        implements TextWatcher {

    private EditText mComposeEditText;
    private ImageButton mSendButton;
    private ComposeMessageViewHost mHost;
    private boolean isEditTextDirty = false;


    public ComposeMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(final ComposeMessageViewHost host) {
        mHost = host;
    }

    private void showHideSendButton(boolean show) {
        TransitionManager.beginDelayedTransition(this);
        if (show) mSendButton.setVisibility(View.VISIBLE);
        else mSendButton.setVisibility(View.GONE);
    }


    @Override
    protected void onFinishInflate() {
        mComposeEditText = findViewById(R.id.compose_message_text);
        mComposeEditText.addTextChangedListener(this);
        mComposeEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (v == mComposeEditText && hasFocus) {
                mComposeEditText.setMaxLines(5);
                mHost.onComposeEditTextFocused();
                if (mComposeEditText.getText() != null && mComposeEditText.getText().length() > 0)
                    mComposeEditText.setSelection(mComposeEditText.getText().length() - 1);
            } else {
                mComposeEditText.setSelection(0);
                mComposeEditText.setMaxLines(1);
            }

        });

        mSendButton = findViewById(R.id.send_button);
        mSendButton.setOnClickListener(v -> sendMessageInternal());
        super.onFinishInflate();
    }

    private void sendMessageInternal() {
        mHost.sendMessage(mComposeEditText.getText().toString().trim());
        mComposeEditText.getText().clear();
        mComposeEditText.clearFocus();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!isEditTextDirty) {
            if (s.length() > 0) {
                isEditTextDirty = true;
                showHideSendButton(true);
            }
        } else {
            if (s.length() == 0) {
                isEditTextDirty = false;
                showHideSendButton(false);

            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void hideAllComposeInputs(boolean b) {
        mComposeEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mComposeEditText.getWindowToken(), 0);
    }

    public interface ComposeMessageViewHost {

        void sendMessage(String message);

        void onComposeEditTextFocused();
    }
}
