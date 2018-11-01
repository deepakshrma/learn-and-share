package com.dbs.ui.utils;

import android.view.View;
import android.view.ViewGroup;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ViewUtilsTest {

    @Test
    public void shouldCallRemoveViewOnParentView() {
        View mockChildView = mock(View.class);
        ViewGroup mockParentView = mock(ViewGroup.class);
        when(mockChildView.getParent()).thenReturn(mockParentView);
        ViewUtils.removeFromParent(mockChildView);

        verify(mockParentView).removeView(mockChildView);
    }
}