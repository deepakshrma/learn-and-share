package com.dbs.replsdk.model;



import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AlternateQuestionsTest {

    AlternateQuestion item;
    AlternateQuestion item2;
    AlternateQuestion item3;
    String QUESTION = "question";
    String QUESTION_ID = "question_id";
    String QUESTION1 = "question1";
    String QUESTION_ID1 = "question_id1";

    @Before
    public void setUp() {
        item = new AlternateQuestion();
        item.setQuestion(QUESTION);
        item.setQuestion_id(QUESTION_ID);

        item2 = new AlternateQuestion();
        item2.setQuestion(QUESTION);
        item2.setQuestion_id(QUESTION_ID);

        item3 = new AlternateQuestion();
        item3.setQuestion(QUESTION1);
        item3.setQuestion_id(QUESTION_ID1);
    }

    @Test
    public void shouldGetQuestion() {
        assertEquals(QUESTION, item.getQuestion());
    }

    @Test
    public void shouldGetQuestionId() {
        assertEquals(QUESTION_ID, item.getQuestion_id());
    }

    @Test
    public void shouldAssertSameItem() {
        assertEquals(true, item.areItemsTheSame(item2));
    }


    @Test
    public void shouldAssertNotSameItem() {
        assertEquals(false, item.areItemsTheSame(item3));
    }

    @Test
    public void shouldAssertAllContentSame() {
        assertEquals(true, item.areContentsTheSame(item2));
    }

    @Test
    public void shouldAssertAllContentNotSame() {
        assertEquals(false, item.areContentsTheSame(item3));
    }
}
