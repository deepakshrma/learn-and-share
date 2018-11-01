package com.dbs.replsdk.ui.items;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dbs.replsdk.R;
import com.dbs.replsdk.model.AlternateQuestion;
import com.dbs.replsdk.ui.components.AlternativeQuestionsView;
import com.dbs.replsdk.ui.events.TextEvent;
import com.dbs.ui.multiadapter.DispatchingItem;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class AlternativeQuestionsItem extends DispatchingItem<AlternativeQuestionsItem.ItemViewHolder> {

    private RecyclerView.RecycledViewPool recycledViewPool;

    private List<AlternateQuestion> cardsList;

    public AlternativeQuestionsItem(long id) {
        super(id);
    }

    AlternativeQuestionsItem withPool(GroupEventDispatcher dispatcher, RecyclerView.RecycledViewPool pool) {
        this.recycledViewPool = pool;
        this.mDispatcher = dispatcher;
        return this;
    }

    public AlternativeQuestionsItem withQuestions(List<AlternateQuestion> questions) {
        this.cardsList = questions;
        return this;
    }

    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        ((AlternativeQuestionsView) holder.getRoot()).updateData(cardsList);
    }

    @NonNull
    @Override
    public ItemViewHolder createViewHolder(@NonNull View itemView) {
        ItemViewHolder holder = new ItemViewHolder(itemView);
        AlternativeQuestionsView cardsView = (AlternativeQuestionsView) holder.getRoot();
        cardsView.setRecycledViewPool(recycledViewPool);
        cardsView.setOnAlternateQuestionsReplyListener(v -> {
            dispatch(new TextEvent(v.getQuestion()));
        });
        (new LinearSnapHelper()).attachToRecyclerView(cardsView);
        return holder;
    }

    @Override
    public int getLayout() {
        return R.layout.item_view_alternative_questions;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AlternativeQuestionsItem)) return false;
        if (cardsList.size() != ((AlternativeQuestionsItem) obj).cardsList.size()) return false;
        int size = cardsList.size();
        for (int i = 0; i < size; i++) {
            if (!cardsList.get(i).equals(((AlternativeQuestionsItem) obj).cardsList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static class ItemViewHolder extends ViewHolder {
        ItemViewHolder(@NonNull View rootView) {
            super(rootView);
        }
    }
}

