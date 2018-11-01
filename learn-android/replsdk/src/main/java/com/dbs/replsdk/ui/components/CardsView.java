package com.dbs.replsdk.ui.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.dbs.replsdk.R;
import com.dbs.replsdk.ui.adaptors.CardButtonList;
import com.dbs.replsdk.uimodel.CardMessageContent;
import com.dbs.ui.utils.ViewUtils;

import java.util.List;

import static com.dbs.ui.utils.ViewUtils.dpToPixels;

public class CardsView extends DBSCarouselView<CardMessageContent.Payload, CardsView.CardViewHolder> {
    private OnCardClickListener onCardClickListener;
    private Context context;
    private CardButtonList.OnCardButtonClickListener onCardButtonClickListener;

    public CardsView(Context context) {
        this(context, null);
    }

    public CardsView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CardsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    public void updateCards(List<CardMessageContent.Payload> cardsList) {
        updateData(cardsList);
    }

    @Override
    public void onViewCreated(Context context, AttributeSet attrs) {
        // Do nothing
    }

    @Override
    public CardViewHolder getViewHolder(@NonNull ViewGroup viewGroup, int i) {
        DBSCardView card = new DBSCardView(context);
        return new CardViewHolder(card);
    }


    @Override
    public void bindData(CardViewHolder holder, CardMessageContent.Payload val, int i) {
        holder.cardView.actionButton1Container.setVisibility(View.GONE);
        if (val.hasButtons()) {
            if (val.getButtons().size() >= 2) {
                holder.cardView.actionButton1Container.setVisibility(View.VISIBLE);
                holder.cardView.actionButton1.setText(val.getButtons().get(0).getLabel());
                holder.cardView.actionButton1.setOnClickListener(v ->
                        onCardButtonClickListener.onClick(val.getButtons().get(0)));

                holder.cardView.actionButton2.setText(val.getButtons().get(1).getLabel());
                holder.cardView.actionButton2.setOnClickListener(v ->
                        onCardButtonClickListener.onClick(val.getButtons().get(1)));


            } else {
                holder.cardView.actionButton2.setText(val.getButtons().get(0).getLabel());
                holder.cardView.actionButton2.setOnClickListener(v ->
                        onCardButtonClickListener.onClick(val.getButtons().get(0)));
            }

        }
        holder.cardView.setCard(val.getMedium().getMediumUrl(), val.getTitle(), val.getSubtitle());
        holder.cardView.setOnClickListener(v -> {
            if (onCardClickListener != null) {
                onCardClickListener.onClick(val);
            }
        });


        if (adapter.getItemCount() == 1) {
            holder.itemView.setBackgroundResource(R.drawable.bg_bubble_rounded);
        } else {
            if (i == 0) {
                holder.itemView.setBackgroundResource(R.drawable.bg_card_left);
            } else if (i == adapter.getItemCount() - 1) {
                holder.itemView.setBackgroundResource(R.drawable.bg_card_right);
            } else {
                holder.itemView.setBackgroundResource(R.drawable.bg_card_middle);
            }
        }

        holder.cardView.forceLayout();
    }

    public void setOnCardButtonClickListener(CardButtonList.OnCardButtonClickListener l) {
        onCardButtonClickListener = l;
    }

    public void setOnCardClickListener(OnCardClickListener listener) {
        this.onCardClickListener = listener;
    }

    public interface OnCardClickListener {
        void onClick(CardMessageContent.Payload cardMessageContent);
    }

    protected static class CardViewHolder extends RecyclerView.ViewHolder {
        DBSCardView cardView;

        CardViewHolder(DBSCardView itemView) {
            super(itemView);
            itemView.setDefaultLayout();
            int[] windowDimensions = ViewUtils.getScreenSize();
            int width = windowDimensions[0] - dpToPixels(itemView.getContext(), 60);
            itemView.setWidth(width);
            cardView = itemView;
        }
    }
}
