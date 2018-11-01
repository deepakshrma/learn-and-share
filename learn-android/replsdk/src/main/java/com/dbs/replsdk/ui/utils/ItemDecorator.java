package com.dbs.replsdk.ui.utils;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by deepak on 6/10/18.
 */

public class ItemDecorator extends RecyclerView.ItemDecoration {
    private final Rect rect;

    public ItemDecorator(Rect rect) {
        this.rect = rect;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(view);
        int post = viewHolder.getAdapterPosition();
        outRect.bottom = rect.bottom;
        outRect.left = rect.left * (post == 0 ? 2 : 1);
        outRect.top = rect.top;
        outRect.right = rect.right + (post == state.getItemCount() - 1 ? rect.left : 0);
    }
}
