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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * A set of validators that are frequently used.
 *
 * @author DBS Bank, AppStudio Team
 */

public final class Validators {
    public static final FormValidateRule NOT_NULL = new NotNullRule("FILED_NOT_NULL");
    public static final FormValidateRule NOT_NULL_NOT_EMPTY = new RequiredRule("FILED_NOT_NULL_NOT_EMPTY");
    public static final FormValidateRule EMAIL_PATTERN = new PatternRule("INVALID_EMAIL_PATTERN", "Invalid email id", Patterns.EMAIL_ADDRESS);

    private Validators() {
        throw new IllegalStateException("Create instance of Validators class");
    }

    /**
     * Rule that validates if input is not null.
     */
    public static class NotNullRule extends ValidationError implements FormValidateRule {
        public NotNullRule(String code, String errorMessage) {
            super(code, errorMessage);
        }

        public NotNullRule(String code) {
            this(code, "");
        }

        @Override
        public boolean validate(@Nullable String data) {
            return data != null;
        }

        @NonNull
        @Override
        public String errorMessage() {
            return !errorMessage.isEmpty() ? errorMessage : "Field should not be empty";
        }
    }

    /**
     * Rule that validates required input is present or not.
     */
    public static class RequiredRule extends NotNullRule implements FormValidateRule {
        public RequiredRule(String code, String errorMessage) {
            super(code, errorMessage);
        }

        public RequiredRule(String code) {
            super(code);
        }

        @Override
        public boolean validate(@Nullable String data) {
            return data != null && !data.isEmpty();
        }

        @NonNull
        @Override
        public String errorMessage() {
            return !errorMessage.isEmpty() ? errorMessage : "Field should not be empty";
        }
    }

    /**
     * Rule that validates if input is of minimum length.
     */
    public static class MinLengthRule extends ValidationError implements FormValidateRule {
        int minStringLength = 10;

        public MinLengthRule(String code, String errorMessage, int len) {
            super(code, errorMessage);
            minStringLength = len;
        }

        public MinLengthRule(String code, int len) {
            this(code, "", len);
        }

        public MinLengthRule(int len) {
            this("FILED_MIN_LENGTH", "", len);
        }

        @Override
        public boolean validate(@Nullable String data) {
            return data != null && data.length() >= minStringLength;
        }

        @NonNull
        @Override
        public String errorMessage() {
            return !errorMessage.isEmpty() ? errorMessage : "Field should not be empty or less than " + minStringLength + " chars";
        }
    }

    /**
     * Rule that validates if input is not more than maximum length.
     */
    public static class MaxLengthRule extends ValidationError implements FormValidateRule {
        private int minStringLength = 10;

        public MaxLengthRule(String code, String errorMessage, int len) {
            super(code, errorMessage);
            minStringLength = len;
        }

        public MaxLengthRule(String code, int len) {
            this(code, "", len);
        }

        public MaxLengthRule(int len) {
            this("FILED_MAX_LENGTH", "", len);
        }

        @Override
        public boolean validate(@Nullable String data) {
            return data != null && data.length() <= minStringLength;
        }

        @NonNull
        @Override
        public String errorMessage() {
            return !errorMessage.isEmpty() ? errorMessage : "Field should not be empty or max than " + minStringLength + " chars";
        }
    }

    /**
     * Rule that validates if input matches pattern.
     */
    public static class PatternRule extends ValidationError implements FormValidateRule {
        private Pattern pattern;

        public PatternRule(String code, String errorMessage, Pattern pattern) {
            super(code, errorMessage);
            this.pattern = pattern;
        }

        public PatternRule(String code, Pattern len) {
            this(code, "", len);
        }

        public PatternRule(Pattern pattern) {
            this("PATTERN_NOT_MATCH", "", pattern);
        }

        @Override
        public boolean validate(@Nullable String data) {
            return this.pattern.matcher(data).find();
        }

        @NonNull
        @Override
        public String errorMessage() {
            return !errorMessage.isEmpty() ? errorMessage : "Field did not match with required pattern";
        }
    }

    /**
     * Error when a validation fails.
     */
    public static class ValidationError {
        String code;
        String errorMessage;

        public ValidationError() {
        }

        public ValidationError(String code, String errorMessage) {
            this.code = code;
            this.errorMessage = errorMessage;
        }
    }
}