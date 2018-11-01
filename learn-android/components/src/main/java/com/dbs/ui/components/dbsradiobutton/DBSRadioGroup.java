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

package com.dbs.ui.components.dbsradiobutton;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * The DBSRadioGroup component is a custom radio group which holds custom radio buttons.
 *
 * @author DBS Bank, AppStudio Team
 * @see DBSRadioButton
 */
public class DBSRadioGroup extends LinearLayout {
    private static final String TAG = DBSRadioButton.class.getSimpleName();
    private int checkedId = View.NO_ID;
    private boolean protectFromCheckedChange;
    private OnCheckedChangeListener onCheckedChangeListener;
    private SparseArray<View> childViewsMap = new SparseArray<>();
    private HierarchyChangeListener hierarchyChangeListener;
    private DBSRadioCheckable.OnCheckedChangeListener childOnCheckedChangeListener;

    public DBSRadioGroup(Context context) {
        super(context);
        setupView();
    }

    public DBSRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    public DBSRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    private void setupView() {
        childOnCheckedChangeListener = new CheckedStateTracker();
        hierarchyChangeListener = new HierarchyChangeListener();
        super.setOnHierarchyChangeListener(hierarchyChangeListener);
    }

    /**
     * Adds a radio button to the radio group
     */
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        final DBSRadioButton dbsRadioButton = getRadioButton(child);
        if (dbsRadioButton != null && dbsRadioButton.isChecked()) {
            changeCheckedState(dbsRadioButton.getId());
        }
        super.addView(child, index, params);
    }

    private DBSRadioButton getRadioButton(View view) {
        if (view instanceof DBSRadioCheckable) {
            return (DBSRadioButton) view;
        } else if (view instanceof DBSRadioButtonHolder) {
            DBSRadioButtonHolder holder = (DBSRadioButtonHolder) view;
            return holder.findViewWithTag(DBSRadioButton.VIEW_TAG);
        }
        return null;
    }

    private void changeCheckedState(int id) {
        protectFromCheckedChange = true;
        if (checkedId != View.NO_ID) {
            setCheckedStateForView(checkedId, false);
            setCheckedId(checkedId, false);
        }
        protectFromCheckedChange = false;
        checkedId = id;
        setCheckedId(id, true);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        hierarchyChangeListener.setOnHierarchyChangeListener(listener);
    }

    private void setCheckedId(@IdRes int id, boolean isChecked) {
        if (onCheckedChangeListener != null) {
            String label = null;
            try {
                DBSRadioButton radioBtn = (DBSRadioButton) childViewsMap.get(id);
                label = (String) radioBtn.getText();
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
            if (isChecked) {
                onCheckedChangeListener.onChecked(this, childViewsMap.get(id), label, id);
            } else {
                onCheckedChangeListener.onUnChecked(this, childViewsMap.get(id), label, id);
            }
        }
    }

    /**
     * Public method to return selected button Id
     *
     * @return
     */
    public int getCheckedId() {
        return checkedId;
    }

    /**
     * sets default selection for given radion button
     * viewId can be either DBSRadioButton's id or DBSRadioButtonHolder's id which has DBSRadioButton as child
     *
     * @param viewId
     * @param checked
     */
    public void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView;
        if (childViewsMap.indexOfKey(viewId) >= 0) {
            checkedView = childViewsMap.get(viewId);
        } else {
            checkedView = findViewById(viewId);
            if (checkedView instanceof DBSRadioButtonHolder) {
                checkedView = checkedView.findViewWithTag(DBSRadioButton.VIEW_TAG);
                if (checkedView != null) {
                    viewId = checkedView.getId();
                } else {
                    viewId = View.NO_ID;
                }
            }
            childViewsMap.put(viewId, checkedView);
        }

        if (checkedView instanceof DBSRadioCheckable) {
            ((DBSRadioCheckable) checkedView).setChecked(checked);
        }
    }

    /**
     * Sets on checked change listener
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    /**
     * When an object of a type is attached to DBSRadioGroup, its methods will
     * be called when state is changed
     */
    public interface OnCheckedChangeListener {
        /**
         * This method is called to notify you that,
         * the radio button was checked.
         */
        void onChecked(View radioGroup, View radioButton, String label, int checkedId);

        /**
         * This method is called to notify you that,
         * the radio button was unchecked.
         */
        void onUnChecked(View radioGroup, View radioButton, String label, int unCheckedId);
    }

    private class CheckedStateTracker implements DBSRadioCheckable.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(View buttonView, boolean isChecked) {
            if (protectFromCheckedChange) {
                return;
            }
            changeCheckedState(buttonView.getId());
        }
    }

    private class HierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener;

        void setOnHierarchyChangeListener(OnHierarchyChangeListener onHierarchyChangeListener) {
            this.onHierarchyChangeListener = onHierarchyChangeListener;
        }

        public void onChildViewAdded(View parent, View child) {
            if (parent == DBSRadioGroup.this) {
                if (child instanceof DBSRadioButtonHolder) {
                    View view = child.findViewWithTag(DBSRadioButton.VIEW_TAG);
                    addListener(view);
                } else {
                    addListener(child);
                }
            }
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        private void addListener(View view) {
            if (view instanceof DBSRadioButton) {
                ((DBSRadioCheckable) view).addOnCheckChangeListener(childOnCheckedChangeListener);
                childViewsMap.put(generateId(view), view);
            }
        }

        private int generateId(View view) {
            int id = view.getId();
            if (id == View.NO_ID) {
                id = View.generateViewId();
                view.setId(id);
            }
            return id;
        }

        public void onChildViewRemoved(View parent, View child) {
            if (parent == DBSRadioGroup.this && child instanceof DBSRadioCheckable) {
                ((DBSRadioCheckable) child).removeOnCheckChangeListener(childOnCheckedChangeListener);
            }
            childViewsMap.remove(child.getId());
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }
}