package com.dbs.replsdk.uimodel;

import android.support.v7.widget.RecyclerView;

import com.dbs.replsdk.ui.items.CardsItem;
import com.dbs.replsdk.ui.items.DisplayOption;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.google.gson.annotations.SerializedName;
import com.xwray.groupie.Group;

import java.util.Collections;
import java.util.List;

public class CardMessageContent extends UiMessageContent<CardMessageContent.Payload> {


    public CardMessageContent() {
        type = MessageContentType.CARD;
    }

    @Override
    public Group createGroup(GroupEventDispatcher groupEventDispatcher, RecyclerView.RecycledViewPool pool, DisplayOption option) {
        return new CardsItem(option.getItemId()).withPool(groupEventDispatcher, pool).withCards(Collections.singletonList(payload));
    }

    public static final class Payload implements com.dbs.replsdk.model.Payload, DifferModel {
        @SerializedName("title")
        private String title;

        @SerializedName("subtitle")
        private String subtitle;

        @SerializedName("buttons")
        private List<Button> buttons;

        @SerializedName("medium")
        private Medium medium;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public List<Button> getButtons() {
            return buttons;
        }

        public void setButtons(List<Button> buttons) {
            this.buttons = buttons;
        }

        public Medium getMedium() {
            return medium;
        }

        public void setMedium(Medium medium) {
            this.medium = medium;
        }

        public boolean hasButtons() {
            return buttons != null && !buttons.isEmpty();
        }

        public Button getFirstButton() {
            return buttons.get(0);
        }

        @Override
        public boolean areItemsTheSame(DifferModel b) {
            if (!(b instanceof Payload)) return false;
            return ((Payload) b).title.equals(this.title) && ((Payload) b).subtitle.equals(this.subtitle);
        }

        @Override
        public boolean areContentsTheSame(DifferModel b) {
            return b.equals(this);
        }
    }
}
