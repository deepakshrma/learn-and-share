package com.dbs.ui.components.forms;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorsTest {

    @Test
    public void shouldReturnFalseWhenNullValidatedWithNotNullRule() {
        FormValidateRule rule = Validators.NOT_NULL;

        assertFalse(rule.validate(null));
    }

    @Test
    public void shouldReturnTrueWhenNonNullValueValidatedWithNotNullRule() {
        FormValidateRule rule = Validators.NOT_NULL;

        assertTrue(rule.validate("someNotNullValue"));
    }

    @Test
    public void shouldReturnDefaultErrorMessageIfNotProvidedInNotNullRule() {
        FormValidateRule rule = Validators.NOT_NULL;

        assertEquals("Field should not be empty", rule.errorMessage());
    }

    @Test
    public void shouldReturnErrorMessageIfProvidedInNotNullRule() {
        String customErrorMessage = "Custom Not Null Error Message";
        FormValidateRule rule = new Validators.NotNullRule("FILED_NOT_NULL", customErrorMessage);

        assertEquals(customErrorMessage, rule.errorMessage());
    }

    @Test
    public void shouldReturnFalseWhenNullValidatedWithRequiredRule() {
        FormValidateRule rule = Validators.NOT_NULL_NOT_EMPTY;

        assertFalse(rule.validate(null));
    }

    @Test
    public void shouldReturnFalseWhenEmptyValueValidatedWithRequiredRule() {
        FormValidateRule rule = Validators.NOT_NULL_NOT_EMPTY;

        assertFalse(rule.validate(""));
    }

    @Test
    public void shouldReturnTrueWhenValidValueValidatedWithRequiredRule() {
        FormValidateRule rule = Validators.NOT_NULL_NOT_EMPTY;

        assertTrue(rule.validate("someNotNullValue"));
    }

    @Test
    public void shouldReturnDefaultErrorMessageIfNotProvidedInRequiredRule() {
        FormValidateRule rule = Validators.NOT_NULL_NOT_EMPTY;

        assertEquals("Field should not be empty", rule.errorMessage());
    }

    @Test
    public void shouldReturnErrorMessageIfProvidedInRequiredRule() {
        String customErrorMessage = "Custom required Error Message";
        FormValidateRule rule = new Validators.RequiredRule("FILED_NOT_NULL", customErrorMessage);

        assertEquals(customErrorMessage, rule.errorMessage());
    }

    @Test
    public void shouldReturnFalseWhenNullValidatedWithMinLengthRule() {
        FormValidateRule rule = new Validators.MinLengthRule(10);

        assertFalse(rule.validate(null));
    }

    @Test
    public void shouldReturnFalseWhenInvalidLengthInMinLengthRule() {
        FormValidateRule rule = new Validators.MinLengthRule(10);

        assertFalse(rule.validate("323dw"));
    }

    @Test
    public void shouldReturnTrueWhenMoreThan10CharactersValueValidatedWithMinLengthRule() {
        FormValidateRule rule = new Validators.MinLengthRule(10);

        assertTrue(rule.validate("some Value with more than 10 characters"));
    }

    @Test
    public void shouldReturnTrueWhenValueWith10CharactersValidatedWithMinLengthRule() {
        FormValidateRule rule = new Validators.MinLengthRule(10);

        assertTrue(rule.validate("1234567890"));
    }

    @Test
    public void shouldReturnDefaultErrorMessageIfNotProvidedInMinLengthRule() {
        FormValidateRule rule = new Validators.MinLengthRule("SOME_CODE", 10);

        assertEquals("Field should not be empty or less than 10 chars", rule.errorMessage());
    }

    @Test
    public void shouldReturnErrorMessageIfProvidedInMinLengthRule() {
        String customErrorMessage = "Custom required Error Message";
        FormValidateRule rule = new Validators.MinLengthRule("FILED_NOT_NULL", customErrorMessage, 10);

        assertEquals(customErrorMessage, rule.errorMessage());
    }

    @Test
    public void shouldReturnFalseWhenNullValidatedWithMaxLengthRule() {
        FormValidateRule rule = new Validators.MaxLengthRule(10);

        assertFalse(rule.validate(null));
    }

    @Test
    public void shouldReturnFalseWhenInvalidLengthInMaxLengthRule() {
        FormValidateRule rule = new Validators.MaxLengthRule(10);

        assertFalse(rule.validate("some Value with more than 10 characters"));
    }

    @Test
    public void shouldReturnTrueWhenLessThan10CharactersValueValidatedWithMaxLengthRule() {
        FormValidateRule rule = new Validators.MaxLengthRule(10);

        assertTrue(rule.validate("< than 10"));
    }

    @Test
    public void shouldReturnTrueWhenValueWith10CharactersValidatedWithMaxLengthRule() {
        FormValidateRule rule = new Validators.MaxLengthRule(10);

        assertTrue(rule.validate("1234567890"));
    }

    @Test
    public void shouldReturnDefaultErrorMessageIfNotProvidedInMaxLengthRule() {
        FormValidateRule rule = new Validators.MaxLengthRule("SOME_CODE", 10);

        assertEquals("Field should not be empty or max than 10 chars", rule.errorMessage());
    }

    @Test
    public void shouldReturnErrorMessageIfProvidedInMaxLengthRule() {
        String customErrorMessage = "Custom required Error Message";
        FormValidateRule rule = new Validators.MaxLengthRule("FILED_NOT_NULL", customErrorMessage, 10);

        assertEquals(customErrorMessage, rule.errorMessage());
    }

    @Test
    public void shouldReturnFalseIfPatternNotMatchedInPatternRule() {
        FormValidateRule rule = new Validators.PatternRule(Pattern.compile("\\d"));

        assertFalse(rule.validate("dsad"));
    }

    @Test
    public void shouldReturnTrueIfPatternMatchesInPatternRule() {
        FormValidateRule rule = new Validators.PatternRule(Pattern.compile("\\d"));

        assertTrue(rule.validate("1321"));
    }

    @Test
    public void shouldReturnDefaultErrorMessageIfNotProvidedInPatternRule() {
        FormValidateRule rule = new Validators.PatternRule("SOME_CODE", Pattern.compile("\\d"));

        assertEquals("Field did not match with required pattern", rule.errorMessage());
    }

    @Test
    public void shouldReturnErrorMessageIfProvidedInPatternRule() {
        String customErrorMessage = "Custom required Error Message";
        FormValidateRule rule = new Validators.PatternRule("SOME_CODE", customErrorMessage, Pattern.compile("\\d"));

        assertEquals(customErrorMessage, rule.errorMessage());
    }
}