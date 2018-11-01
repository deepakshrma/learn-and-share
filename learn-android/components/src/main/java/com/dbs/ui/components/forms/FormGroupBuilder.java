package com.dbs.ui.components.forms;

import android.content.Context;

import java.util.HashMap;

public class FormGroupBuilder extends HashMap<String, FormGroup> {
    public static FormGroup build(Context context) {
        return new FormGroup(context);
    }

}
