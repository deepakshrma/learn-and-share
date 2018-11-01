package com.dbs.ui.components.forms;

import java.util.HashMap;

public class FormGroupError extends HashMap<String, String> {
    @Override
    public String toString() {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("{");
        for (Entry<String, String> errorEntry : this.entrySet()) {
            stringBuffer.append(errorEntry.getKey()).append(": ").append(errorEntry.getValue()).append(",\n");
        }
        stringBuffer.append("}");
        return super.toString();
    }
}