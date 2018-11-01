package com.dbs.replsdk.ui.items;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.util.Linkify;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.dbs.replsdk.R;
import com.dbs.replsdk.databinding.ItemViewChatMessageBinding;
import com.dbs.replsdk.uimodel.TextMessageContent;
import com.xwray.groupie.databinding.BindableItem;

public class TextMessageItem extends BindableItem<ItemViewChatMessageBinding> {

    private final TextMessageContent.Payload payload;
    private final boolean isMe;
    private final int maxWidth;
    private final int fixedWidth;
    private final int bg;

    public TextMessageItem(TextMessageContent.Payload payload, DisplayOption option) {
        super(option.getItemId());
        this.isMe = option.isIsMe();
        this.maxWidth = option.getMaxWidth();
        this.bg = option.getBgRes();
        this.fixedWidth = option.getFixedWidth();
        this.payload = payload;
        if (isMe) {// add
            getExtras().put("offset", new Rect(0, 16, 16, 16));
        } else {
            int bottom = (bg != R.drawable.bg_bubble_middle && bg != R.drawable.bg_bubble_top) ? 8 : 0;
            getExtras().put("offset", new Rect(16, bg == R.drawable.bg_bubble_top_left_excluded ? 16 : 0, 0, bottom));
        }
    }


    @Override
    public void bind(@NonNull ItemViewChatMessageBinding viewBinding, int position) {
        final Context context = viewBinding.getRoot().getContext();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewBinding.textViewMessage.getLayoutParams();
        params.gravity = isMe ? Gravity.END : Gravity.START;
        params.width = fixedWidth == 0 ? FrameLayout.LayoutParams.WRAP_CONTENT : context.getResources().getDimensionPixelSize(this.fixedWidth);
        viewBinding.textViewMessage.setLayoutParams(params);

        viewBinding.textViewMessage.setTextColor(isMe ? Color.WHITE : Color.BLACK);

        Drawable bgDrawable = ContextCompat.getDrawable(context, bg);
        if (bgDrawable != null) {
            int color = ContextCompat.getColor(context, isMe ? R.color.my_bubble : R.color.their_bubble);
            bgDrawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
            viewBinding.textViewMessage.setBackground(bgDrawable);
        }
        viewBinding.textViewMessage.setMaxWidth(context.getResources().getDimensionPixelSize(this.maxWidth));
        viewBinding.textViewMessage.setTextViewHTML(payload.getText());
        Linkify.addLinks(viewBinding.textViewMessage, Linkify.ALL);

    }

    @Override
    public int getLayout() {
        return R.layout.item_view_chat_message;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TextMessageItem)) return false;
        if (isMe != ((TextMessageItem) obj).isMe) return false;
        return payload.getText().equals(((TextMessageItem) obj).payload.getText());
    }
}
