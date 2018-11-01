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

package com.dbs.ui.components;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbs.components.R;

import static android.view.View.GONE;

/**
 * The DBSDialog component is to create dialog (with call to actions)
 *
 * @author DBS Bank, AppStudio Team
 */
public class DBSDialog extends DialogFragment implements View.OnClickListener {

    public static final int NIL_VALUE = -1;
    private DialogOnClickHandler dialogOnClickHandler;
    private View dialogViewFormat;

    protected void setDialogOnClickHandler(DialogOnClickHandler dialogOnClickHandler) {
        this.dialogOnClickHandler = dialogOnClickHandler;
    }

    protected void setDialogView(View v) {
        this.dialogViewFormat = v;
    }

    /**
     * @inheritDoc
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dbs_dialog, null);
        View buttonsView;
        LinearLayout buttonsHolder = view.findViewById(R.id.buttonsHolder);
        Bundle bundle = getArguments();
        int imageId = bundle.getInt("image");
        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String positiveButtonText = bundle.getString("positiveButtonText");
        String negativeButtonText = bundle.getString("negativeButtonText");
        int positiveButtonColor = bundle.getInt("positiveButtonColor");
        int negativeButtonColor = bundle.getInt("negativeButtonColor");
        boolean isVerticalButtons = bundle.getBoolean("isVerticalButtons");

        if (isVerticalButtons) {
            buttonsView = inflater.inflate(R.layout.dbs_dialog_button_vertical, buttonsHolder);
        } else {
            buttonsView = inflater.inflate(R.layout.dbs_dialog_button_horizontal, buttonsHolder);
        }
        Button dialogPositiveButton = buttonsView.findViewById(R.id.dialogPositiveButton);
        Button dialogNegativeButton = buttonsView.findViewById(R.id.dialogNegativeButton);

        LinearLayout dialogViewHolder = view.findViewById(R.id.dialogViewHolder);
        if (dialogViewFormat != null) {
            dialogViewHolder.addView(dialogViewFormat);
        } else {
            View v = inflater.inflate(R.layout.dialog_format_default, dialogViewHolder);
            ImageView dialogImage = v.findViewById(R.id.dialogImage);
            TextView dialogTitle = v.findViewById(R.id.dialogTitle);
            TextView dialogDescription = v.findViewById(R.id.dialogDescription);
            isValidText(dialogTitle, title);
            isValidText(dialogDescription, description);
            if (imageId != NIL_VALUE) {
                dialogImage.setImageResource(imageId);
            } else {
                dialogImage.setVisibility(GONE);
            }
        }

        setButton(dialogPositiveButton, positiveButtonText, positiveButtonColor);
        setButton(dialogNegativeButton, negativeButtonText, negativeButtonColor);

        dialogPositiveButton.setOnClickListener(this);
        dialogNegativeButton.setOnClickListener(this);

        builder.setView(view);
        return builder.create();
    }

    private void setButton(Button button, String text, int color) {
        if (text != null && !text.isEmpty()) {
            button.setText(text);
            if (color != NIL_VALUE) {
                button.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
            }
        } else {
            button.setVisibility(GONE);
        }
    }

    private void isValidText(TextView textView, String text) {
        if (text != null && !text.isEmpty()) {
            textView.setText(text);
        } else {
            textView.setVisibility(GONE);
        }
    }

    /**
     * @inheritDoc
     * Invoke positive/negative button click callback and dismiss the dialog
     */
    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        if (dialogOnClickHandler != null) {
            if (viewID == R.id.dialogPositiveButton) {
                dialogOnClickHandler.positiveOnclick(this);
                this.dismiss();
            } else if (viewID == R.id.dialogNegativeButton) {
                dialogOnClickHandler.negativeOnclick(this);
                this.dismiss();
            }
        }
    }

    /**
     * When an object of a type is attached to DBSDialog, its methods will
     * be called when buttons are clicked
     */
    public interface DialogOnClickHandler {
        /**
         * This method is called to notify you that, positive button was clicked
         * @param dialogFragment dialog fragment clicked
         */
        void positiveOnclick(DialogFragment dialogFragment);

        /**
         * This method is called to notify you that, negative button was clicked
         * @param dialogFragment dialog fragment clicked
         */
        void negativeOnclick(DialogFragment dialogFragment);
    }

    public static class Builder {
        int imageResourceId;
        String title;
        String description;
        View dialogView;
        private String positiveButtonText;
        private String negativeButtonText;
        private DialogOnClickHandler dialogOnClickHandler;
        private int positiveButtonColor;
        private int negativeButtonColor;
        private boolean isVerticalButtons;

        /**
         * Creates a builder for a DBSDialog with title and description.
         *
         * @param title title for Dialog
         * @param description description for Dialog
         */
        public Builder(String title, String description) {
            this.title = title;
            this.description = description;
            this.imageResourceId = NIL_VALUE;
            this.isVerticalButtons = false;
        }

        /**
         * Creates a builder for a DBSDialog with custom view.
         *
         * @param v view for Dialog
         */
        public Builder(View v) {
            this.dialogView = v;
            this.isVerticalButtons = false;
        }

        /**
         * Set the image resource id.
         *
         * @param imageResourceId image resource id for dialog
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withImage(int imageResourceId) {
            this.imageResourceId = imageResourceId;
            return this;
        }

        /**
         * Set the button orientation to vertical.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setVerticalButtons() {
            this.isVerticalButtons = true;
            return this;
        }

        /**
         * Set the positive button text and color.
         *
         * @param positiveButtonText  text for positive button
         * @param positiveButtonColor color for positive button
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withPositiveButton(String positiveButtonText, int positiveButtonColor) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonColor = positiveButtonColor;
            return this;
        }

        /**
         * Set the positive button text.
         *
         * @param positiveButtonText text for positive button
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withPositiveButton(String positiveButtonText) {
            return withPositiveButton(positiveButtonText, NIL_VALUE);
        }

        /**
         * Set the negative button text and color.
         *
         * @param negativeButtonText  text for negative button
         * @param negativeButtonColor color for negative button
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withNegativeButton(String negativeButtonText, int negativeButtonColor) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonColor = negativeButtonColor;
            return this;
        }

        /**
         * Set the negative button text.
         *
         * @param negativeButtonText text for negative button
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder withNegativeButton(String negativeButtonText) {
            return withNegativeButton(negativeButtonText, NIL_VALUE);
        }

        /**
         * Set click handler.
         *
         * @param dialogOnClickHandler click handler for dialog
         * @return This Builder object to allow for chaining of calls to set methods
         * @see DialogOnClickHandler
         */
        public Builder withDialogClickHandler(DialogOnClickHandler dialogOnClickHandler) {
            this.dialogOnClickHandler = dialogOnClickHandler;
            return this;
        }

        /**
         * Creates an {@link DBSDialog} with the arguments supplied to this
         * builder.
         */
        public DBSDialog build() {
            DBSDialog dbsDialog = new DBSDialog();
            dbsDialog.setDialogOnClickHandler(dialogOnClickHandler);
            Bundle bundle = new Bundle();

            if (dialogView != null) {
                dbsDialog.setDialogView(dialogView);
            } else {
                dbsDialog.setDialogView(null);
                bundle.putInt("image", imageResourceId);
                bundle.putString("title", title);
                bundle.putString("description", description);
            }
            bundle.putString("positiveButtonText", positiveButtonText);
            bundle.putString("negativeButtonText", negativeButtonText);
            bundle.putInt("positiveButtonColor", positiveButtonColor);
            bundle.putInt("negativeButtonColor", negativeButtonColor);
            bundle.putBoolean("isVerticalButtons", isVerticalButtons);
            dbsDialog.setArguments(bundle);
            return dbsDialog;
        }
    }
}