package com.dbs.ui.styleable;

/**
 * StyleableComponent :
 * Interface to all Styleable Component
 */
public interface BiStyleableComponent {
    /**
     * Component with composite views can override to accept multiple styles
     *
     * @param style1
     * @param style2
     */
    void applyStyle(int style1, int style2);
}
