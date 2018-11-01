package com.dbs.ui.styleable;

import android.support.annotation.StyleRes;

/**
 * StyleableComponent :
 * Interface to all Styleable Component
 */
public interface CompositeStyleableComponent {
    /**
     * Component with composite views(more than 2) can override to accept multiple styles
     *
     * @param style
     * @param rest
     */
    void applyStyle(int style, @StyleRes int... rest);
}
