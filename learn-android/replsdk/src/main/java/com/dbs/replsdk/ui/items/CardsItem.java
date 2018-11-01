package com.dbs.replsdk.ui.items;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dbs.replsdk.R;
import com.dbs.replsdk.ui.components.CardsView;
import com.dbs.replsdk.ui.events.ButtonEvent;
import com.dbs.replsdk.uimodel.CardMessageContent;
import com.dbs.ui.multiadapter.DispatchingItem;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class CardsItem extends DispatchingItem<CardsItem.CardsItemViewHolder> {

    private RecyclerView.RecycledViewPool recycledViewPool;
    private List<CardMessageContent.Payload> cardsList;

    public CardsItem(long id) {
        super(id);
    }

    public CardsItem withPool(GroupEventDispatcher dispatcher, RecyclerView.RecycledViewPool pool) {
        this.recycledViewPool = pool;
        this.mDispatcher = dispatcher;
        return this;
    }

    public CardsItem withCards(List<CardMessageContent.Payload> cards) {
        this.cardsList = cards;
        return this;
    }

    @Override
    public void bind(@NonNull CardsItemViewHolder holder, int position) {
        ((CardsView) holder.getRoot()).updateCards(cardsList);
    }

    @NonNull
    @Override
    public CardsItemViewHolder createViewHolder(@NonNull View itemView) {
        CardsItemViewHolder holder = new CardsItemViewHolder(itemView);
        CardsView cardsView = (CardsView) holder.getRoot();
        cardsView.setRecycledViewPool(recycledViewPool);
        cardsView.setOnCardButtonClickListener(v -> {
            dispatch(new ButtonEvent(v));
        });
        (new LinearSnapHelper()).attachToRecyclerView(cardsView);
        return holder;
    }

    @Override
    public int getLayout() {
        return R.layout.item_view_cards;
    }

    static class CardsItemViewHolder extends ViewHolder {
        CardsItemViewHolder(@NonNull View rootView) {
            super(rootView);
        }
    }
}
