package com.dbs.replsdk.ui.items;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.dbs.replsdk.R;
import com.dbs.replsdk.databinding.ItemButtonInListBinding;
import com.dbs.replsdk.ui.events.TextEvent;
import com.dbs.replsdk.uimodel.CardMessageContent;
import com.dbs.ui.multiadapter.DispatchingBindableItem;
import com.dbs.ui.multiadapter.GroupEventDispatcher;

public class ButtonInListItem extends DispatchingBindableItem<ItemButtonInListBinding> {

    private final CardMessageContent.Payload payload;
    private final int bg;


    public ButtonInListItem(GroupEventDispatcher dispatcher, CardMessageContent.Payload payload, DisplayOption option) {
        super(option.getItemId());
        this.mDispatcher = dispatcher;
        this.payload = payload;
        this.bg = option.getBgRes();
        getExtras().put("offset", new Rect(16, 1, 0, 0));
    }


    @Override
    public void bind(@NonNull ItemButtonInListBinding viewBinding, int position) {
        Drawable bgDrawable = ContextCompat.getDrawable(viewBinding.getRoot().getContext(), bg);
        if (bgDrawable != null) {
            viewBinding.getRoot().setBackground(bgDrawable);
        }
        viewBinding.setPayload(payload);
        viewBinding.btnLabel.setOnClickListener(v -> dispatch(new TextEvent(payload.getTitle())));
    }

    @Override
    public int getLayout() {
        return R.layout.item_button_in_list;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ButtonInListItem)) return false;
        return payload.getTitle().equals(((ButtonInListItem) obj).payload.getTitle());
    }
}
