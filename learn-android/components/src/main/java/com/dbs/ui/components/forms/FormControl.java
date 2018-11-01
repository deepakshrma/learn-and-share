package com.dbs.ui.components.forms;

import android.widget.EditText;

import java.util.LinkedHashSet;

public class FormControl extends LinkedHashSet<FormValidateRule> {
    private EditText view;
    private String error;

    public FormControl(EditText editText) {
        view = editText;
    }

    public String getError() {
        return error;
    }

    public String getValue() {
        return view != null ? view.getText().toString() : null;
    }

    protected boolean validate(String data) {
        error = null;
        for (FormValidateRule it : this) {
            if (!it.validate(data)) {
                error = it.errorMessage();
                break;
            }
        }
        return error == null;
    }

    @Override
    public boolean equals(Object object) {
        return this == object;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}