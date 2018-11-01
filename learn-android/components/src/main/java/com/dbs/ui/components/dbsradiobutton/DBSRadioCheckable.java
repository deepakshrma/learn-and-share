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

package com.dbs.ui.components.dbsradiobutton;

import android.view.View;
import android.widget.Checkable;

/**
 * When an object of a type is attached to DBSRadioGroup, its methods will
 * be called when listeners are added or removed
 *
 * @author DBS Bank, AppStudio Team
 */
interface DBSRadioCheckable extends Checkable {
    /**
     * This method is called to notify you that,
     * new listener is to be added.
     */
    void addOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);

    /**
     * This method is called to notify you that,
     * the listener needs to be removed.
     */
    void removeOnCheckChangeListener(OnCheckedChangeListener onCheckedChangeListener);

    /**
     * When an object of a type is attached to DBSRadioButton, its methods will
     * be called when state changes
     */
    interface OnCheckedChangeListener {
        /**
         * This method is called to notify you that,
         * the radio button state is changed
         */
        void onCheckedChanged(View buttonView, boolean isChecked);
    }
}