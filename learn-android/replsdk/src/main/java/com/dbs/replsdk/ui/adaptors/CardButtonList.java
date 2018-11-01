package com.dbs.replsdk.ui.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dbs.replsdk.R;
import com.dbs.replsdk.uimodel.Button;
import com.dbs.ui.components.DBSTextView;

import java.util.List;

public class CardButtonList extends ArrayAdapter<Button> {
    private OnCardButtonClickListener onCardButtonClickListener;

    public CardButtonList(Context context, List<Button> buttons) {
        super(context, 0, buttons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Button button = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.button_list_item_view, parent, false);
        }
        ((DBSTextView) convertView).setText(button.getLabel());
        convertView.setOnClickListener(v -> {
            if (onCardButtonClickListener != null) {
                onCardButtonClickListener.onClick(button);
            }
        });
        return convertView;
    }

    public void setOnCardButtonClickListener(OnCardButtonClickListener l) {
        onCardButtonClickListener = l;
    }

    public interface OnCardButtonClickListener {
        void onClick(Button button);
    }

}
