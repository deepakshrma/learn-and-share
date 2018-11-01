package com.dbs.ui.components.utils;

import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

public class AndroidTestUtils {
    public static List<View> getChildViewsOf(Class claz, ViewGroup viewGroup) {
        List<View> listOfViews = new ArrayList<View>();
        for (int i = 0, childCount = viewGroup.getChildCount(); i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (claz.isInstance(view))
                listOfViews.add(view);
        }
        return listOfViews;
    }
}
