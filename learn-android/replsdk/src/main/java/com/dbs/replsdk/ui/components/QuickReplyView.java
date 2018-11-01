package com.dbs.replsdk.ui.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.dbs.replsdk.R;
import com.dbs.replsdk.model.QuickReply;
import com.dbs.ui.components.DBSTextView;
import com.dbs.ui.utils.StringUtils;
import com.dbs.ui.utils.ThemeUtils;

import java.util.List;

import static com.dbs.ui.utils.ViewUtils.dpToPixels;

public class QuickReplyView extends DBSCarouselView<QuickReply, QuickReplyView.QuickReplyViewHolder> {
    OnQuickReplyListener onItemClickListener;

    public QuickReplyView(Context context) {
        super(context);
    }

    public QuickReplyView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public QuickReplyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateReplies(List<QuickReply> quickReplyList) {
        updateData(quickReplyList);
    }

    public void setOnQuickReplyListener(OnQuickReplyListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onViewCreated(Context context, AttributeSet attrs) {

    }

    @Override
    public QuickReplyViewHolder getViewHolder(@NonNull ViewGroup ViewGroup, int i) {
        final int commonPadding = dpToPixels(ViewGroup.getContext(), 8);
        DBSTextView button = (DBSTextView) inflate(getContext(), R.layout.dbs_quick_reply_item_view, null);
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        button.setLayoutParams(params);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        button.setPadding(commonPadding * 2, commonPadding, commonPadding * 2, commonPadding);
        button.setTextColor(ThemeUtils.getThemeColor(getContext(), R.attr.colorAccent));
        button.setBackgroundResource(R.drawable.bg_quickreply_rounded);
        return new QuickReplyViewHolder(button);
    }


    @Override
    public void bindData(QuickReplyViewHolder holder, QuickReply val, int i) {
        if (!StringUtils.isEmpty(val.getText()))
            holder.quickReplyBtn.setText(val.getText());
        else if (!StringUtils.isEmpty(val.getPayload()))
            holder.quickReplyBtn.setText(val.getPayload());
        holder.quickReplyBtn.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onReply(val);
            }
        });
    }

    public interface OnQuickReplyListener {
        void onReply(QuickReply quickReply);
    }

    public static class QuickReplyViewHolder extends RecyclerView.ViewHolder {
        DBSTextView quickReplyBtn;

        QuickReplyViewHolder(DBSTextView itemView) {
            super(itemView);
            quickReplyBtn = itemView;
        }
    }
}