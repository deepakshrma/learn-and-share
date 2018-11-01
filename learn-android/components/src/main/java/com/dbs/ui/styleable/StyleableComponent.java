package com.dbs.ui.styleable;

import android.support.annotation.StyleRes;

/**
 * StyleableComponent :
 * Interface to all Styleable Component
 */
public interface StyleableComponent {
    /**
     * This has to override for simple use component
     *
     * @param style
     */
    void applyStyle(@StyleRes int style);
}
