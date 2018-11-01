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

/**
 * Rule to be validated
 *
 * @author DBS Bank, AppStudio Team
 */
public interface FormValidateRule {

    /**
     * Method for your validation logic
     */
    boolean validate(String data);

    /**
     * The error message that will be sent, if the <b>validate</b> method returns <b>false</b>
     */
    default String errorMessage() {
        return "";
    }
}