package com.dbs.replsdk.ui.items;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dbs.replsdk.R;
import com.dbs.replsdk.ui.events.ButtonEvent;
import com.dbs.replsdk.uimodel.Button;
import com.dbs.ui.multiadapter.DispatchingItem;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.xwray.groupie.ViewHolder;

public class ButtonItem extends DispatchingItem {

    private final Button payload;
    private final int bg;
    private final int fixedWidth;

    public ButtonItem(GroupEventDispatcher dispatcher, Button payload, DisplayOption option) {
        super(option.getItemId());
        this.payload = payload;
        this.mDispatcher = dispatcher;
        this.bg = option.getBgRes();
        this.fixedWidth = option.getFixedWidth();
        getExtras().put("offset", new Rect(16, 1, 0, bg == R.drawable.bg_bubble_bottom ? 8 : 0));

    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        final Context context = viewHolder.getRoot().getContext();
        Drawable bgDrawable = ContextCompat.getDrawable(context, bg);
        if (bgDrawable != null) {
            viewHolder.getRoot().setBackground(bgDrawable);
        }
        ViewGroup.LayoutParams params = viewHolder.getRoot().getLayoutParams();
        params.width = fixedWidth == 0 ? FrameLayout.LayoutParams.WRAP_CONTENT : context.getResources().getDimensionPixelSize(this.fixedWidth);
        ((TextView) viewHolder.getRoot()).setText(payload.getLabel());
        viewHolder.getRoot().setOnClickListener(v -> dispatch(new ButtonEvent(payload)));
    }

    @Override
    public int getLayout() {
        return R.layout.item_view_single_button;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ButtonItem)) return false;
        return payload.getPayload().equals(((ButtonItem) obj).payload.getPayload());
    }
}