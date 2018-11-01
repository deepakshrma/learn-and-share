package com.dbs.ui.multiadapter;


/**
 * A base class that take a {@link Class} as params and {@link #getKey()} should return {@link Class#hashCode()}
 */
public abstract class ClassToKey {

    private Class mClass;

    /**
     * should call this to get the key
     *
     * @param klass
     */
    ClassToKey(Class klass) {
        mClass = klass;
    }

    /**
     * Get key
     *
     * @return
     */
    public int getKey() {
        return mClass.hashCode();
    }
}