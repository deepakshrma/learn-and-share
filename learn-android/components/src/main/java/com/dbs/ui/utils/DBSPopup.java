package com.dbs.ui.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbs.components.R;


/**
 * DBSPopup help to create dialog/popup with custom view and handle basic functionality like validation on closing
 */

public class DBSPopup extends DialogFragment {
    @LayoutRes
    int dialogResId;
    View viewHolder;
    OnPopupCloseListener onPopupCloseListener;

    /**
     * Create dialog with layout resource id
     * [default is no cancelable dialog]
     *
     * @param resId
     * @return
     */
    public static DBSPopup createDialog(@LayoutRes int resId) {
        DBSPopup dbsDialogCustom = new DBSPopup();
        dbsDialogCustom.setCancelable(false);
        dbsDialogCustom.dialogResId = resId;
        return dbsDialogCustom;
    }

    /**
     * Create cancelable dialog with layout resource id
     * [default is no cancelable dialog]
     *
     * @param resId
     * @return
     */
    public static DBSPopup createCancelableDialog(@LayoutRes int resId) {
        DBSPopup dbsDialogCustom = createDialog(resId);
        dbsDialogCustom.setCancelable(true);
        return dbsDialogCustom;
    }

    /**
     * Create dialog with inflated view. This is helpful when view is dynamic. ie. created on runtime
     * [default is no cancelable dialog]
     *
     * @param view
     * @return
     */
    public static DBSPopup createDialog(View view) {
        DBSPopup dbsDialogCustom = new DBSPopup();
        dbsDialogCustom.setCancelable(false);
        dbsDialogCustom.dialogResId = -1;
        dbsDialogCustom.viewHolder = view;
        return dbsDialogCustom;
    }

    /**
     * Create cancelable dialog with inflated view. This is helpful when view is dynamic. ie. created on runtime
     * [default is no cancelable dialog]
     *
     * @param view
     * @return
     */
    public static DBSPopup createCancelableDialog(View view) {
        DBSPopup dbsDialogCustom = createDialog(view);
        dbsDialogCustom.setCancelable(true);
        return dbsDialogCustom;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (dialogResId != -1) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            viewHolder = inflater.inflate(dialogResId, null);
        }
        setButtonClickListeners();
        return builder.setView(viewHolder).create();
    }

    @Override
    public void onDestroyView() {
        if (viewHolder != null) {
            ViewGroup parentViewGroup = (ViewGroup) viewHolder.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
        super.onDestroyView();
    }

    /**
     * Get the view holder of the DBSPopup
     *
     * @return
     */
    public View getViewHolder() {
        return viewHolder;
    }

    /**
     * This is to set onPopupCloseListener, This is called whenever user pressed on either positive button or negative button
     *
     * @param listener
     */
    public void setOnPopupCloseListener(OnPopupCloseListener listener) {
        onPopupCloseListener = listener;
    }

    private void setButtonClickListeners() {
        View closeBtn = viewHolder.findViewById(R.id.dbs_popup_button_cancel);
        View submitBtn = viewHolder.findViewById(R.id.dbs_popup_button_done);
        if (closeBtn != null) {
            closeBtn.setOnClickListener(v -> {
                this.dismiss();
                if (onPopupCloseListener != null) {
                    onPopupCloseListener.negativeOnclick(this);
                }
            });
        }
        if (submitBtn != null) {
            submitBtn.setOnClickListener(v -> {
                this.dismiss();
                if (onPopupCloseListener != null) {
                    onPopupCloseListener.positiveOnclick(this);
                }
            });
        }
    }

    public interface OnPopupCloseListener {
        /**
         * This method is called to notify you that, positive button was clicked
         *
         * @param popupFragment dialog fragment clicked
         */
        void positiveOnclick(DBSPopup popupFragment);

        /**
         * This method is called to notify you that, negative button was clicked
         *
         * @param popupFragment dialog fragment clicked
         */
        void negativeOnclick(DBSPopup popupFragment);
    }

}
