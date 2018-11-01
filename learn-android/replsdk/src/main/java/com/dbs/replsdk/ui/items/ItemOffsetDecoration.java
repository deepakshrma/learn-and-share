package com.dbs.replsdk.ui.items;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dbs.ui.utils.ViewUtils;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {


    private final Context context;

    public ItemOffsetDecoration(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        ViewHolder viewHolder = (ViewHolder) parent.getChildViewHolder(view);
        Item item = viewHolder.getItem();
        Rect offset = (Rect) item.getExtras().get("offset");
        if (offset != null) {
            outRect.left = ViewUtils.dpToPixels(context, offset.left);
            outRect.right = ViewUtils.dpToPixels(context, offset.right);
            outRect.top = ViewUtils.dpToPixels(context, offset.top);
            outRect.bottom = ViewUtils.dpToPixels(context, offset.bottom);
        }

    }
}
