package com.dbs.replsdk.ui.items;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;

import com.dbs.replsdk.R;
import com.dbs.replsdk.ui.components.DBSLoadingDots;
import com.xwray.groupie.Item;

public class ReplyingItem extends Item<ReplyingItem.ReplyingViewHolder> {

    public ReplyingItem() {
        super();
        getExtras().put("offset", new Rect(16, 8, 0, 8));

    }

    @NonNull
    @Override
    public ReplyingViewHolder createViewHolder(@NonNull View itemView) {
        return new ReplyingViewHolder(itemView);
    }

    @Override
    public void unbind(@NonNull ReplyingViewHolder holder) {
        super.unbind(holder);
        holder.dots.stopAnimation();
    }

    @Override
    public void bind(@NonNull ReplyingViewHolder viewHolder, int position) {
        viewHolder.dots.startAnimation();
    }

    @Override
    public int getLayout() {
        return R.layout.item_view_replying;
    }

    static class ReplyingViewHolder extends com.xwray.groupie.ViewHolder {

        final DBSLoadingDots dots;

        ReplyingViewHolder(@NonNull View rootView) {
            super(rootView);
            dots = rootView.findViewById(R.id.dots);
            dots.setAutoPlay(false);
        }
    }


}
