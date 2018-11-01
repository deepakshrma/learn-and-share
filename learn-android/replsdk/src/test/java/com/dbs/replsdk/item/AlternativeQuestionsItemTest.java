package com.dbs.replsdk.item;

import android.support.annotation.NonNull;

import com.dbs.replsdk.model.AlternateQuestion;
import com.dbs.replsdk.ui.components.AlternativeQuestionsView;
import com.dbs.replsdk.ui.items.AlternativeQuestionsItem;
import com.dbs.ui.multiadapter.DispatchingItem;
import com.xwray.groupie.ViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AlternativeQuestionsItemTest {

    @Mock
    AlternativeQuestionsView view;


    List<AlternateQuestion> cardsList;
    private AlternativeQuestionsItem item;

    @Before
    public void setUp() {


        MockitoAnnotations.initMocks(this);

        cardsList = new ArrayList<>();
        AlternateQuestion question1 = new AlternateQuestion();
        question1.setQuestion("question1");
        question1.setQuestion_id("question1");
        cardsList.add(question1);
        item = new AlternativeQuestionsItem(1000L);

        item.withQuestions(cardsList);


    }

    @Test
    public void shouldGetCorrectId() {
        assertEquals(1000L, item.getId());
    }

    @Test
    public void testView() {
        AlternativeQuestionsItem.ItemViewHolder holder = item.createViewHolder(view);
        assertThat(holder.getRoot(), instanceOf(AlternativeQuestionsView.class));
    }

    @Test
    public void testBindData() {

        AlternativeQuestionsItem.ItemViewHolder holder = item.createViewHolder(view);
        item.bind(holder, 0);
        verify(view, times(1)).updateData(cardsList);
    }

    @Test
    public void testEquals() {

        DummyItem dummyItem = new DummyItem();

        assertEquals(false, item.equals(dummyItem));

        AlternativeQuestionsItem diff = new AlternativeQuestionsItem(2000L).withQuestions(Collections.emptyList());
        assertEquals(false, item.equals(diff));

        AlternativeQuestionsItem eq = new AlternativeQuestionsItem(1000L).withQuestions(cardsList);
        assertEquals(true, item.equals(eq));
    }

    public static class DummyItem extends DispatchingItem {

        @Override
        public void bind(@NonNull ViewHolder viewHolder, int position) {

        }

        @Override
        public int getLayout() {
            return 0;
        }
    }

}
