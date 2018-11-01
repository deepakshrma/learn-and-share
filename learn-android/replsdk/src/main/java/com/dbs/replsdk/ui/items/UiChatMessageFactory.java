package com.dbs.replsdk.ui.items;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.dbs.replsdk.R;
import com.dbs.replsdk.model.AlternateQuestion;
import com.dbs.replsdk.uimodel.ContainerMessageContent;
import com.dbs.replsdk.uimodel.MessageContentType;
import com.dbs.replsdk.uimodel.TextMessageContent;
import com.dbs.replsdk.uimodel.UiChatMessage;
import com.dbs.replsdk.uimodel.UiMessageContent;
import com.dbs.ui.multiadapter.GroupEventDispatcher;
import com.dbs.ui.multiadapter.GroupItemFactory;
import com.xwray.groupie.Group;
import com.xwray.groupie.Section;

import java.util.List;

public class UiChatMessageFactory extends GroupItemFactory<UiChatMessage> {

    private static final long MILLION = 1000000;
    private static final long GRAND = 1000;
    private final RecyclerView.RecycledViewPool alternativeQuestionsPool;
    private final RecyclerView.RecycledViewPool cardsPool;


    public UiChatMessageFactory() {
        super(UiChatMessage.class);
        this.alternativeQuestionsPool = new RecyclerView.RecycledViewPool();
        this.cardsPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public Group create(@Nullable UiChatMessage model, @Nullable GroupEventDispatcher dispatcher) {

        Section section = new Section();

        int index = 0;

        if (model == null) return section;

        List<UiMessageContent> messageContents = model.getMessageContents();
        if (messageContents != null && messageContents.size() > 0) {
            int length = messageContents.size();
            if (length > 1) {
                /*
                 * IF THE FIRST MESSAGE IS `TEXT` AND THE NEXT IS `CONTAINER` AND MODE IS `LIST`
                 * SET FULL ROUNDED CORNER FOR `TEXT` AND MIDDLE AND BOTTOM FOR THE REST
                 */
                if (messageContents.get(0).getType() == MessageContentType.TEXT
                        && messageContents.get(1).getType() == MessageContentType.CONTAINER
                        && (((ContainerMessageContent) messageContents.get(1)).getPayload().isList())
                        ) {
                    for (index = 0; index < length; index++) {
                        section.add(messageContents.get(index).createGroup(dispatcher, cardsPool, new DisplayOption()
                                .withIsMe(model.isMe())
                                .withBgRes(index == 0 ? R.drawable.bg_bubble_top : index == length - 1 ? R.drawable.bg_bubble_bottom : R.drawable.bg_bubble_middle)
                                .withFixedWidth(R.dimen.dbs_button_list_item_width)
                                .withItemId(model.getId() + index * GRAND)));
                    }
                }
                /*
                 *   ELSE , IF THERE ARE JUST `TEXT` AND  `BUTTON`s
                 */
                else if (messageContents.get(0).getType() == MessageContentType.TEXT
                        && messageContents.get(1).getType() == MessageContentType.BUTTON) {
                    for (index = 0; index < length; index++) {
                        section.add(messageContents.get(index).createGroup(dispatcher, cardsPool, new DisplayOption()
                                .withIsMe(model.isMe())
                                .withBgRes(index == 0 ? R.drawable.bg_bubble_top : index == length - 1 ? R.drawable.bg_bubble_bottom : R.drawable.bg_bubble_middle)
                                .withFixedWidth(R.dimen.dbs_item_with_button_width)
                                .withItemId(model.getId() + index * GRAND)));
                    }
                }
                /*
                 * CONSIDER THE REST ARE THE SAME, SET ROUNDED FOR ALL 4 CORNER
                 */
                else {
                    for (index = 0; index < length; index++) {
                        section.add(messageContents.get(index).createGroup(dispatcher, cardsPool, new DisplayOption()
                                .withIsMe(model.isMe())
                                .withItemId(model.getId() + index * GRAND)));
                    }
                }
            } else {  // NORMALLY,
                section.add(messageContents.get(0).createGroup(dispatcher, cardsPool, new DisplayOption()
                        .withBgRes(R.drawable.bg_bubble_rounded)
                        .withIsMe(model.isMe())
                        .withItemId(model.getId())));
            }
        }

        /*
         * now handler alternative questions
         */
        List<AlternateQuestion> questions = model.getAlternateQuestions();
        if (questions != null && questions.size() > 0) {
            section.add(new TextMessageItem(
                    new TextMessageContent.Payload("You may also ask: "),
                    new DisplayOption()
                            .withBgRes(R.drawable.bg_bubble_top_left_excluded)
                            .withItemId(model.getId() + index * MILLION))
            );
            section.add(new AlternativeQuestionsItem(model.getId())
                    .withPool(dispatcher, alternativeQuestionsPool)
                    .withQuestions(questions));
        }
        return section;
    }

}
