package com.dbs.ui.components.forms;

import android.support.design.widget.TextInputEditText;
import android.text.Editable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DBSTextInputCardLayoutGroupTest {
    @Mock
    DBSTextInputCardLayout emailInput, firstNameInput;

    @Test
    public void testIsValidInputCalledOnEveryElement() {
        DBSTextInputCardLayoutGroup textInputLayouts = new DBSTextInputCardLayoutGroup();
        textInputLayouts.add(emailInput);
        textInputLayouts.add(firstNameInput);

        textInputLayouts.isInputValidForGroup();

        verify(emailInput, times(1)).isValidInput();
        verify(firstNameInput, times(1)).isValidInput();
    }

    @Test
    public void testHandleInputIsCalledIfInputIsInvalid() {
        DBSTextInputCardLayoutGroup textInputLayouts = new DBSTextInputCardLayoutGroup();

        TextInputEditText inputEditTextMock = mock(TextInputEditText.class);
        Editable editableMock = mock(Editable.class);
        when(editableMock.toString()).thenReturn("ABC");
        when(inputEditTextMock.getText()).thenReturn(editableMock);
        Whitebox.setInternalState(emailInput, "rules", Collections.singletonList(mock(Validators.PatternRule.class)));
        when(emailInput.getTextInputEditTextView()).thenReturn(inputEditTextMock);

        textInputLayouts.add(emailInput);
        assertFalse(textInputLayouts.isInputValidForGroup());
        verify(emailInput, times(1)).setError(null);
    }

    @Test
    public void testMethodReturnsTrueIfInputIsValid() {
        DBSTextInputCardLayoutGroup textInputLayouts = new DBSTextInputCardLayoutGroup();

        TextInputEditText inputEditTextMock = mock(TextInputEditText.class);
        Editable editableMock = mock(Editable.class);
        when(editableMock.toString()).thenReturn("ABC");
        when(inputEditTextMock.getText()).thenReturn(editableMock);
        Validators.PatternRule mockValidator = mock(Validators.PatternRule.class);
        when(mockValidator.validate("ABC")).thenReturn(true);
        Whitebox.setInternalState(emailInput, "rules", Collections.singletonList(mockValidator));
        when(emailInput.getTextInputEditTextView()).thenReturn(inputEditTextMock);

        textInputLayouts.add(emailInput);

        boolean inputValidForGroup = textInputLayouts.isInputValidForGroup();

        assertTrue(inputValidForGroup);
    }
}
