package com.dbs.ui.components.forms;

import android.content.Context;

import java.util.HashMap;

public class FormGroup extends HashMap<String, FormControl> {

    private Context context;


    private FormValidationType type = FormValidationType.BASIC;
    private FormGroupError errors;
    private int errorsCount = -1;

    FormGroup(Context context) {
        this.context = context;
    }

    public FormValidationType getType() {
        return type;
    }

    public int getErrorsCount() {
        return errorsCount;
    }

    public Context getContext() {
        return context;
    }

    FormGroup setValidationType(FormValidationType formValidationType) {
        this.type = formValidationType;
        return this;
    }

    public FormGroup add(String id, FormControl formControl) {
        this.put(id, formControl);
        return this;
    }


    public boolean isValid() {
        this.validate();
        return errors.isEmpty();
    }

    public FormGroupError getErrors() {
        return errors;
    }

    private FormGroupError validate() {
        errors = new FormGroupError();
        for (Entry<String, FormControl> formControlEntry : this.entrySet()) {
            FormControl formControl = formControlEntry.getValue();
            String id = formControlEntry.getKey();
            if (!formControl.validate(formControl.getValue())) {
                errors.put(id, formControl.getError());
            }
        }
        return errors;
    }

    @Override
    public boolean equals(Object object) {
        return this == object;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    public enum FormValidationType {
        BASIC,
        COLORATION
    }
}