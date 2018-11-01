/*
 * - Licensed Materials - Property of DBS Bank SG
 * - "Restricted Materials of DBS Bank"
 *
 * APP Studio SDK, Copyright (c) 2018. DBS Bank SG
 *
 * Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 */

package com.dbs.ui.components.forms;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * The DBSTextInputCardLayoutGroup is custom HashSet with a list of DBSTextInputLayout.
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSTextInputCardLayoutGroup extends LinkedHashSet<DBSTextInputCardLayout> {

    /**
     * Returns if all the inputs are valid in a group
     */
    public boolean isInputValidForGroup() {
        Iterator<DBSTextInputCardLayout> iterator = iterator();
        boolean isValidGroup = true;
        while (iterator.hasNext()) {
            DBSTextInputCardLayout reactiveTextInput = iterator.next();
            if (!reactiveTextInput.isValidInput()) {
                isValidGroup = false;
                reactiveTextInput.handleInput();
            }
        }
        return isValidGroup;
    }
}
