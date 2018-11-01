package com.dbs.ui;

import android.view.View;

import static junit.framework.Assert.assertTrue;

public class ViewAssertions {

    public static void assertAbove(View viewAbove, View viewBelow) {
        int[] positionOfViewAbove = new int[2];
        viewAbove.getLocationInWindow(positionOfViewAbove);

        int[] positionOfViewBelow = new int[2];
        viewBelow.getLocationInWindow(positionOfViewBelow);

        float endOfViewAbove_y = positionOfViewAbove[1] + viewAbove.getHeight() - 1;
        assertTrue("view " + viewAbove + " is not above view " + viewBelow + " endLocation 'y' of viewAbove: " + endOfViewAbove_y + " y of viewBelow: " + positionOfViewBelow[1], endOfViewAbove_y <= positionOfViewBelow[1]);
    }

    public static void assertRight(View rightView, View leftView) {
        int[] positionOfRightView = new int[2];
        rightView.getLocationInWindow(positionOfRightView);

        int[] positionOfLeftView = new int[2];
        leftView.getLocationInWindow(positionOfLeftView);

        float endOfLeftView_x = positionOfLeftView[0] + leftView.getWidth() - 1;
        assertTrue("view " + rightView + " is not to the right of view " + leftView + " end of leftView: " + endOfLeftView_x + " x of rightView: " + positionOfRightView[0], positionOfRightView[0] >= endOfLeftView_x);
    }
}
