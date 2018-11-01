package com.dbs.replsdk.uimodel;

import android.support.v7.widget.RecyclerView;

import com.dbs.replsdk.R;
import com.dbs.replsdk.ui.items.ButtonInListItem;
import com.dbs.replsdk.ui.items.CardsItem;
import com.dbs.replsdk.ui.items.DisplayOption;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.google.gson.annotations.SerializedName;
import com.xwray.groupie.Group;
import com.xwray.groupie.Section;

import java.util.ArrayList;
import java.util.List;

import static com.dbs.replsdk.uimodel.ContainerMessageContent.Payload.MODE_LIST;

public class ContainerMessageContent extends UiMessageContent<ContainerMessageContent.Payload> {

    public ContainerMessageContent() {
        type = MessageContentType.CONTAINER;
    }

    @Override
    public Group createGroup(GroupEventDispatcher groupEventDispatcher, RecyclerView.RecycledViewPool pool, DisplayOption option) {

        Section section = new Section();
        long id = option.getItemId();
        if (MODE_LIST.equals(payload.mode)) {
            List<UiMessageContent> contents = payload.cardMessageContents;
            int count = 0;
            int contentsSize = contents.size();
            for (UiMessageContent content : contents) {
                if (content.payload instanceof CardMessageContent.Payload) {
                    CardMessageContent.Payload payload = (CardMessageContent.Payload) content.payload;
                    section.add(new ButtonInListItem(groupEventDispatcher, payload,
                            option.withItemId(id + count + 1)
                                    .withBgRes(count < contentsSize - 1 ? R.drawable.bg_bubble_middle : R.drawable.bg_bubble_bottom))
                    );
                }
                count++;

            }
        } else { //carousel
            List<CardMessageContent.Payload> payloads = new ArrayList<>();
            for (UiMessageContent content : payload.cardMessageContents) {
                if (content.payload instanceof CardMessageContent.Payload) {
                    payloads.add((CardMessageContent.Payload) content.payload);
                }
            }

            section.add(new CardsItem(option.getItemId()).withPool(groupEventDispatcher, pool).withCards(payloads));

        }

        return section;
    }

    public static class Payload implements com.dbs.replsdk.model.Payload {

        public static final String MODE_CAROUSEL = "CAROUSEL";
        public static final String MODE_LIST = "LIST";

        @SerializedName("mode")
        private String mode; // CAROUSEL OR LIST
        @SerializedName("contents")
        private List<UiMessageContent> cardMessageContents;

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public List<UiMessageContent> getCardMessageContents() {
            return cardMessageContents;
        }

        public void setCardMessageContents(List<UiMessageContent> cardMessageContents) {
            this.cardMessageContents = cardMessageContents;
        }

        public boolean isList() {
            return MODE_LIST.equals(mode);
        }

        public boolean isCarousel() {
            return MODE_CAROUSEL.equals(mode);
        }

    }

}